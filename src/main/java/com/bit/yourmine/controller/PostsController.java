package com.bit.yourmine.controller;

import com.bit.yourmine.domain.files.Files;
import com.bit.yourmine.domain.users.Role;
import com.bit.yourmine.domain.users.SessionUser;
import com.bit.yourmine.dto.posts.PostsResponseDto;
import com.bit.yourmine.dto.posts.PostsSaveRequestDto;
import com.bit.yourmine.dto.posts.PostsUpdateRequestDto;
import com.bit.yourmine.service.AttentionService;
import com.bit.yourmine.service.ChatService;
import com.bit.yourmine.service.PostsService;
import com.bit.yourmine.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.bit.yourmine.controller.MainController.indexPage;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;
    private final ReviewService reviewService;
    private final AttentionService attentionService;
    private final ChatService chatService;
    @Value("${kakao.js.key}")
    private String kakaoKey;

    @GetMapping("/posts/save")
    public String postsSave(Model model) {
        model.addAttribute("kakaoKey", kakaoKey);
        return "post/postSave";
    }

    @PostMapping("/posts/save")
    public String save(@ModelAttribute PostsSaveRequestDto requestDto, MultipartHttpServletRequest files, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("userInfo");
        requestDto.setAuthor(user.getId());
        Long id = postsService.save(requestDto);
        List<MultipartFile> images = files.getFiles("image");
        for (MultipartFile image: images) {
            postsService.imageSave(image, id);
        }
        return "redirect:/";
    }

    @PostMapping("/posts/modify")
    public String postModify(@ModelAttribute PostsUpdateRequestDto requestDto, MultipartHttpServletRequest files) {
        postsService.update(requestDto);
        List<MultipartFile> images = files.getFiles("image");
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                postsService.imageSave(image, requestDto.getId());
            }
        }
        return "redirect:/posts/"+requestDto.getId();
    }

    @GetMapping("/posts/{id}")
    public String postsInfo(@PathVariable Long id, Model model, HttpSession session,
                            @CookieValue(name="hitCheck") String cookie, HttpServletResponse response) {
        PostsResponseDto post = postsService.findById(id);
        PostsResponseDto post2 = postsService.findById(id);
        model.addAttribute("post", post);
        SessionUser sessionUser = (SessionUser) session.getAttribute("userInfo");
        final Long cursor = 0L;
        List<PostsResponseDto> hitList = postsService.findByHit(indexPage, cursor);
        model.addAttribute("hitPost", hitList);
        String way = post.getWay();
        String wayToString = "";
        if (way.contains("A")) {
            String addr = post2.getUsers().getAddress();
            String[] array = addr.split(" ");
            addr = array[0] + " " + array[1];
            post2.getUsers().setAddress(addr);
            model.addAttribute("post2", post2);
            wayToString += " 직거래";
        }
        if (way.contains("B")) {
            wayToString += " 택배거래";
        }
        model.addAttribute("attention", attentionService.getAttentionCount(post.getId()));
        model.addAttribute("chat", chatService.getChatCount(post.getId()));
        model.addAttribute("way", wayToString);
        List<Files> withOutThumbnail = new ArrayList<>();
        withOutThumbnail.addAll(post.getFilesList());
        withOutThumbnail.remove(0);
        if (withOutThumbnail.size() >= 1) {
            model.addAttribute("files", withOutThumbnail);
        }
        try {
            // Post Author or Admin
            if (sessionUser.getId().equals(post.getAuthor()) || sessionUser.getRole().equals(Role.ADMIN)) {
                model.addAttribute("owner", "owner");
            }
        } catch (NullPointerException e) {
            System.out.println("guest");
        }
        if (!cookie.contains(String.valueOf(id))) {
            cookie += id+"/";
            postsService.hitUpdate(id);
        }
        response.addCookie(new Cookie("hitCheck", cookie));
        return "post/postInfo";
    }

    @GetMapping("/posts/modify/{id}")
    public String postModify(@PathVariable Long id, Model model) {
        model.addAttribute("kakaoKey", kakaoKey);
        PostsResponseDto post = postsService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute(post.getCategory(), "selected");
        try {
            if (post.getOfSize() != null) {
                String ofSize = post.getOfSize();
                String size1 = ofSize.substring(0, ofSize.indexOf("*"));
                String size2 = ofSize.substring(ofSize.indexOf("*")+1, ofSize.lastIndexOf("*"));
                String size3 = ofSize.substring(ofSize.lastIndexOf("*")+1);
                model.addAttribute("size1", size1);
                model.addAttribute("size2", size2);
                model.addAttribute("size3", size3);
            }
        } catch (Exception e) {
            System.out.println("size null");
        }
        String way = post.getWay();
        if (way.contains("A")) {
            model.addAttribute("A", "A");
        }
        if (way.contains("B")) {
            model.addAttribute("B", "B");
        }
        return "post/postModify";
    }

    @GetMapping("/posts/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postsService.delete(id);
        return "redirect:/";
    }
}
