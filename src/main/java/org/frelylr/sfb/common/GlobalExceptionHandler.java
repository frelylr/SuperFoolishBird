package org.frelylr.sfb.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * global exception handler
     */
    @ExceptionHandler
    public ModelAndView globalExceptionHandler(Exception e) {

        LOGGER.error("【SYSTEM ERROR】", e);
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.setViewName("error");

        return mav;
    }
}
