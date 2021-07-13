package com.bit.yourmain.controller;

import com.bit.yourmain.domain.files.Files;
import com.bit.yourmain.domain.users.Role;
import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.dto.posts.PostsSaveRequestDto;
import com.bit.yourmain.dto.posts.PostsUpdateRequestDto;
import com.bit.yourmain.dto.reviews.ReviewResponseDto;
import com.bit.yourmain.dto.reviews.ReviewScoreSetDto;
import com.bit.yourmain.service.PostsService;
import com.bit.yourmain.service.ReviewService;
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

import static com.bit.yourmain.controller.MainController.indexPage;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;
    private final ReviewService reviewService;
    @Value("${kakao.js.key}")
    private String kakaoKey;

    @GetMapping("/posts/save")
    public String postsSave(Model model) {
        model.addAttribute("kakaoKey", kakaoKey);
        return "post/postSave";
    }

    @PostMapping("/posts/save")
    public String save(@ModelAttribute PostsSaveRequestDto requestDto, MultipartHttpServletRequest files) {
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
        try {
            List<MultipartFile> images = files.getFiles("image");
            for (MultipartFile image : images) {
                postsService.imageSave(image, requestDto.getId());
            }
        } catch (Exception e) {
            System.out.println("no image");
        }
        return "redirect:/posts/"+requestDto.getId();
    }

    @GetMapping("/posts/{id}")
    public String postsInfo(@PathVariable Long id, Model model, HttpSession session,
                            @CookieValue(name="hitCheck") String cookie, HttpServletResponse response) {
        PostsResponseDto post = postsService.findById(id);
        model.addAttribute("post", post);
        SessionUser sessionUser = (SessionUser) session.getAttribute("userInfo");
        final Long cursor = 0L;
        List<PostsResponseDto> hitList = postsService.findByHit(indexPage, cursor);
        model.addAttribute("hitPost", hitList);
        String way = post.getWay();
        String wayToString = "";
        if (way.contains("A")) {
            wayToString += " 직거래";
        }
        if (way.contains("B")) {
            wayToString += " 택배거래";
        }
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
            System.out.println("비 로그인");
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
        try {
            String way = post.getWay();
            if (way.contains("A")) {
                model.addAttribute("A", "A");
            }
            if (way.contains("B")) {
                model.addAttribute("B", "B");
            }
        } catch (Exception e) {
            System.out.println("way null");
        }
        return "post/postModify";
    }

    @GetMapping("/posts/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postsService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/review/{position}/{no}")
    public String postReview(@PathVariable Long no, @PathVariable String position, Model model) {
        ReviewResponseDto reviewResponseDto = reviewService.getReview(no);
        reviewResponseDto.setPosition(position);
        ReviewScoreSetDto scoreSetDto = new ReviewScoreSetDto(reviewResponseDto);
        if (position.equals("buyer")) {
            scoreSetDto.setId(reviewResponseDto.getSeller());
        } else {
            scoreSetDto.setId(reviewResponseDto.getBuyer());
        }
        model.addAttribute("review", scoreSetDto);
        return "post/postReview";
    }

    @PutMapping("/review/set")
    public String setScore(@RequestBody ReviewScoreSetDto scoreSetDto) {
        reviewService.setScore(scoreSetDto);
        return "account/myPage";
    }
}
