package com.bit.yourmain.controller;

import com.bit.yourmain.domain.SessionUser;
import com.bit.yourmain.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UsersService usersService;

    @GetMapping("/loginpage")
    public String loginpage(@RequestParam(value = "error", required = false) String error, HttpSession session, Model model) {

        SessionUser user = null;
        try {
            user = (SessionUser) session.getAttribute("userInfo");
        } catch (Exception e) {
            System.out.println("로그인 정보 없음");
        }
        if (user != null) {
            return "index";
        }

        if (error != null) {
            String errCode = null;
            if (error.equals("err1")) {
                errCode = "존재하지 않는 아이디입니다";
            } else if (error.equals("err2")) {
                errCode = "아이디 또는 비밀번호가 틀렸습니다";
            } else {
                errCode = "로그인 실패";
            }
            String content = "<b style=\"color:red;\">" + errCode + "</b>";
            model.addAttribute("error", content);
        }
        return "account/loginpage";
    }

    @GetMapping("/signup")
    public String signup() {
        return "account/signup";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "account/mypage";
    }

    @GetMapping("/userModify")
    public String userModify() {
        return "account/modify/userModify";
    }

    @GetMapping("/passwordModify")
    public String passwordModify() {
        return "account/modify/passwordModify";
    }

    @PostMapping("/profileModify")
    public String profileModify(@RequestParam MultipartFile profile, @RequestParam String id, HttpSession session) {
        SessionUser users = (SessionUser) session.getAttribute("userInfo");
        users.setProfile(usersService.profileModify(profile, id));
        session.removeAttribute("userInfo");
        session.setAttribute("userInfo" , users);
        return "account/mypage";
    }

    @GetMapping("/delProfile")
    public String delProfile(HttpSession session) {
        SessionUser users = (SessionUser) session.getAttribute("userInfo");
        SessionUser sessionUser = new SessionUser(usersService.delProfile(users.getId()));
        session.removeAttribute("userInfo");
        session.setAttribute("userInfo" , sessionUser);
        return "account/mypage";
    }
}
