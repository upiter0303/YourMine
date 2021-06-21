package com.bit.yourmain.controller;

import com.bit.yourmain.domain.posts.Posts;
import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        if (sessionUser.getId().equals(post.getAuthor())) {
            model.addAttribute("owner", "owner");
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
}
