package org.frelylr.sfb.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RequestErrorHandler implements ErrorController {

    /**
     * request error handler
     */
    @RequestMapping(value = "/error")
    public ModelAndView error(HttpServletResponse response) {

        ModelAndView mav = new ModelAndView();
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            mav.setViewName("error404");
        } else {
            mav.setViewName("error");
        }

        return mav;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
