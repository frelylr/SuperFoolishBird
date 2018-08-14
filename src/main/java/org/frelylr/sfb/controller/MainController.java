package org.frelylr.sfb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    /**
     * main page
     */
    @RequestMapping("/main")
    public String main() {

        return "main";
    }
}
