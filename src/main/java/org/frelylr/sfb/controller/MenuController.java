package org.frelylr.sfb.controller;

import org.frelylr.sfb.common.MessagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MenuController {

    @Autowired
    private Environment env;

    @Autowired
    private MessagesUtils messagesUtil;


    /**
     * menu page
     */
    @GetMapping(value = "/{anyPath}/menu")
    public String menu(@PathVariable("anyPath") String anyPath, Model model) {

        model.addAttribute("anyPath", anyPath);
        model.addAttribute("ok", messagesUtil.getMessage("ok"));

        return "menu";
    }
}
