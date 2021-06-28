package com.bit.yourmain.controller;

import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        List<PostsResponseDto> postsList = postsService.findAllDesc();
        model.addAttribute("posts", postsList);
        List<PostsResponseDto> hitList = postsService.findByHit();
        model.addAttribute("hitPost", hitList);

        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute("userInfo");
            List<PostsResponseDto> areaList = postsService.findByAddress(sessionUser.getAddress());
            model.addAttribute("areaPost", areaList);
        } catch (NullPointerException e) {
            System.out.println("null address");
        }
        return "index";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "account/accessDenied";
    }
}
