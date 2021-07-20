package com.bit.yourmine.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentException(Exception e) {
        logger.error(e.toString());
        return "deny/errorPage";
    }

    @ExceptionHandler(Exception.class)
    public String Exception(Exception e) {
        logger.error(e.toString());
        for (StackTraceElement mes: e.getStackTrace()) {
            logger.warn(String.valueOf(mes));
        }
        return "deny/errorPage";
    }
}
