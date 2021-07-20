package com.bit.yourmine.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errMsg = null;
        if (exception instanceof AuthenticationServiceException) {
            errMsg = "err1";

        } else if (exception instanceof BadCredentialsException) {
            errMsg = "err2";

        } else if (exception instanceof SessionAuthenticationException) {
            errMsg = "err3"; // 중복 로그인
        } else {
            errMsg = "login error";
        }

        setDefaultFailureUrl("/loginPage?error="+errMsg);

        super.onAuthenticationFailure(request,response,exception);
    }
}
