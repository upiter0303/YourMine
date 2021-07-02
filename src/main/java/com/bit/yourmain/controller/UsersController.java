package com.bit.yourmain.controller;

import com.bit.yourmain.domain.attention.Attention;
import com.bit.yourmain.domain.posts.Posts;
import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.dto.chat.ChatResponseDto;
import com.bit.yourmain.dto.chat.ChatRoomListDto;
import com.bit.yourmain.dto.posts.PostsResponseDto;
import com.bit.yourmain.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UsersController {

    private final UsersService usersService;
    private final PostsService postsService;
    private final AttentionService attentionService;
    private final ChatService chatService;
    private final ReviewService reviewService;

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, HttpSession session, Model model) {

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
            } else if (error.equals("err3")) {
                errCode = "이미 접속중인 계정입니다";
            } else {
                errCode = "로그인 실패";
            }
            String content = "<b style=\"color:red;\">" + errCode + "</b>";
            model.addAttribute("error", content);
        }
        return "account/loginPage";
    }

    @GetMapping("/signup")
    public String signup() {
        return "account/signup";
    }

    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("userInfo");
        Users users = usersService.getUsers(sessionUser.getId());
        try {
            List<PostsResponseDto> myPosts = new ArrayList<>();
            for (Posts posts : users.getPosts()) {
                myPosts.add(new PostsResponseDto(posts));
                model.addAttribute("posts", myPosts);
            }
        } catch (NullPointerException e) {
            System.out.println("no posts");
        }
        try {
            List<Long> longList = attentionService.findAllByUsersNo(sessionUser.getNo());
            List<PostsResponseDto> postsList = postsService.findByAttention(longList);
            model.addAttribute("attention", postsList);
        } catch (NullPointerException e) {
            System.out.println("no attention");
        }
        try {
            List<ChatRoomListDto> sellRoomList = new ArrayList<>();
            List<Posts> myPosts = users.getPosts();
            for (Posts posts: myPosts) {
                sellRoomList.addAll(chatService.getChatList(posts.getId()));
            }
            model.addAttribute("sellChat", sellRoomList);
        } catch (NullPointerException e) {
            System.out.println("no sellChat");
        }
        try {
            List<ChatRoomListDto> buyRoomList = chatService.getBuyList(sessionUser.getId());
            model.addAttribute("buyChat", buyRoomList);
        } catch (NullPointerException e) {
            System.out.println("no buyChat");
        }
        try {
            model.addAttribute("sellReview", reviewService.getSellReview(sessionUser.getId()));
        } catch (NullPointerException e) {
            System.out.println("no SellReview");
        }
        try {
            model.addAttribute("buyReview", reviewService.getBuyReview(sessionUser.getId()));
        } catch (NullPointerException e) {
            System.out.println("no BuyReview");
        }
        return "account/myPage";
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
        return "account/myPage";
    }

    @GetMapping("/delProfile")
    public String delProfile(HttpSession session) {
        SessionUser users = (SessionUser) session.getAttribute("userInfo");
        SessionUser sessionUser = new SessionUser(usersService.delProfile(users.getId()));
        session.removeAttribute("userInfo");
        session.setAttribute("userInfo" , sessionUser);
        return "account/myPage";
    }
}
