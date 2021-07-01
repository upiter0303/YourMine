package com.bit.yourmain.controller;

import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(HttpSession session, Model model, HttpServletResponse response) {
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
        Cookie cookie = new Cookie("hitCheck", null);
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "index";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "account/accessDenied";
    }

    @GetMapping("/chat/{no}/{id}")
    public String chat(@PathVariable Long no, @PathVariable String id, Model model) {
        model.addAttribute("roomId", no + "-" + id);
        model.addAttribute("post", postsService.findById(no));
        return "chat/chat";
    }
}
