package com.bit.yourmine.config.interceptor;

import com.bit.yourmine.domain.users.Role;
import com.bit.yourmine.domain.users.SessionUser;
import com.bit.yourmine.dto.reviews.ReviewResponseDto;
import com.bit.yourmine.service.PostsService;
import com.bit.yourmine.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Configuration
public class CustomInterceptor extends HandlerInterceptorAdapter {

    private final PostsService postsService;
    private final ReviewService reviewService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("userInfo");
        String userId = sessionUser.getId();
        String ser = String.valueOf(request.getServletPath());
        String check = ser.substring(1, ser.substring(1).indexOf("/")+1);
        String end = ser.substring(ser.lastIndexOf("/")+1);
        String toMid = ser.substring(ser.substring(1).indexOf("/")+1).substring(1);
        String mid = toMid.substring(0, toMid.indexOf("/"));

        // admin 권한 설정
        if(sessionUser.getRole().equals(Role.ADMIN)) {
            return true;
        }

        switch (check) {
            case "chat":
                if (userId.equals(end)) {
                    return true;
                }
                if (postsService.findById(Long.valueOf(mid)).getUsers().getId().equals(userId)) {
                    return true;
                }
                break;
            case "posts":
                if (userId.equals(postsService.findById(Long.valueOf(end)).getUsers().getId())) {
                    return true;
                }
                break;
            case "review":
                ReviewResponseDto review = reviewService.getReviewPosition(Long.valueOf(end), userId);
                if (review.getBuyer().equals(userId) && review.getPosition().equals(mid)) {
                    return true;
                } else if (review.getSeller().equals(userId) && review.getPosition().equals(mid)) {
                    return true;
                }
                break;
        }
        response.sendRedirect(request.getContextPath()+"/authDenied");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
