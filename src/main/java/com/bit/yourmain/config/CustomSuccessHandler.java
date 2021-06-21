package com.bit.yourmain.config;

import com.bit.yourmain.domain.users.Role;
import com.bit.yourmain.domain.users.SessionUser;
import com.bit.yourmain.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final UsersService usersService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (session.getAttribute("userInfo") != null) {
            session.removeAttribute("userInfo");
        }
        SessionUser users = new SessionUser(usersService.getUsers(authentication.getName()));
        session.setAttribute("userInfo" , users);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());
        grantedAuthorities.add(new SimpleGrantedAuthority(users.getRole().getValue()));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        response.sendRedirect("/");
    }

}