package com.bit.yourmain.controller.api;

import com.bit.yourmain.domain.users.Role;
import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.dto.users.PasswordModifyDto;
import com.bit.yourmain.dto.users.UserModifyDto;
import com.bit.yourmain.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UsersApiController {
    private final UsersService usersService;

    @PostMapping("/signup")
    public void save(@RequestBody Users users) {
        usersService.save(users);
    }

    @PostMapping("/idCheck")
    public Object idCheck(@RequestBody Users users) {
        Users checkUser = null;
        try {
            checkUser = usersService.getUsers(users.getId());
        } catch (NoSuchElementException e) {
            System.out.println("계정 없음");
        }

        boolean check;
        check = checkUser == null;

        Map<String, Object> map = new HashMap<>();
        map.put("result", "success");
        map.put("data", check);
        return map;
    }

    @PostMapping("/userModify")
    public void userModify(@RequestBody UserModifyDto modifyDto, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("userinfo");
        sessionUser = usersService.userModify(modifyDto, sessionUser);
        session.removeAttribute("userInfo");
        session.setAttribute("userInfo" , sessionUser);
        // 계정의 권한 재설정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }


    @PostMapping("/passwordModify")
    public void passwordModify(@RequestBody PasswordModifyDto modifyDto) {
        usersService.passwordModify(modifyDto);
    }

    @PostMapping("/findId")
    public String findId(@RequestBody String phone) {
        return usersService.findId(phone);
    }

    @GetMapping("/leave/{id}")
    public void leave(@PathVariable String id) {
        usersService.leave(id);
    }
}
