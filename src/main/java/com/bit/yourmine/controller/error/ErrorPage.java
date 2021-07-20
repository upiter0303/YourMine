package com.bit.yourmine.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPage implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(ErrorPage.class);

    @GetMapping("/error")
    public String errorHandle(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));
        logger.warn(String.valueOf(httpStatus));
        return "deny/errorPage";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
