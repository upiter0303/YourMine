package com.bit.yourmain.controller;

import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        List<PostsResponseDto> postsList = postsService.findAllDesc();
        model.addAttribute("posts", postsList);
        return "index";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "account/accessDenied";
    }
}
