package com.bit.yourmain.controller;

import com.bit.yourmain.domain.posts.Posts;
import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.dto.posts.PostsSaveRequestDto;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/posts/save")
    public String postsSave() {
        return "post/postSave";
    }

    @PostMapping("/posts/save")
    public String save(@ModelAttribute PostsSaveRequestDto requestDto, MultipartHttpServletRequest files) {
        Long id = postsService.save(requestDto);
        System.out.println(requestDto.getWay());
        List<MultipartFile> images = files.getFiles("image");
        for (MultipartFile image: images) {
            postsService.imageSave(image, id);
        }
        return "redirect:/";
    }

    // Posts Searching
    @GetMapping("/posts/search")
    public String search(String keyword, Model model) {
        List<Posts> searchList = postsService.search(keyword);
        model.addAttribute("searchList", searchList);
        return "post/postSearch";
    }

    @GetMapping("/posts/{id}")
    public String postsInfo(@PathVariable Long id, Model model, HttpSession session) {
        PostsResponseDto post = postsService.findById(id);
        model.addAttribute("post", post);
        SessionUser sessionUser = (SessionUser) session.getAttribute("userInfo");
        try {
            if (sessionUser.getId().equals(post.getAuthor())) {
                model.addAttribute("owner", "owner");
            }
        } catch (NullPointerException e) {
            System.out.println("비 로그인");
        }
        return "post/postInfo";
    }

    @GetMapping("/posts/modify/{id}")
    public String postModify(@PathVariable Long id, Model model) {
        PostsResponseDto post = postsService.findById(id);
        model.addAttribute("post", post);
        String status = post.getStatus();
        String value = "selected";
        if (status.equals("거래대기")) {
            model.addAttribute("posting", value);
        } else if (status.equals("거래중")) {
            model.addAttribute("deal", value);
        } else {
            model.addAttribute("end", value);
        }
        return "post/postModify";
    }

    @GetMapping("/posts/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postsService.delete(id);
        return "redirect:/";
    }
}
