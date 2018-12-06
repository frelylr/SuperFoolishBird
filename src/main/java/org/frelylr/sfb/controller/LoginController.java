package org.frelylr.sfb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.frelylr.sfb.common.CommonUtils;
import org.frelylr.sfb.common.Constants;
import org.frelylr.sfb.common.MessagesUtils;
import org.frelylr.sfb.dto.AdminUserMasterDto;
import org.frelylr.sfb.dto.LanguageDto;
import org.frelylr.sfb.form.LoginForm;
import org.frelylr.sfb.service.LoginService;
import org.frelylr.sfb.session.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
public class LoginController {

    @Autowired
    private MessagesUtils messagesUtil;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private LoginService loginService;

    /**
     * login page
     */
    @RequestMapping("/")
    public String init(Model model, HttpServletRequest req, HttpServletResponse res) {

        // TODO FL
        List<LanguageDto> languages = new ArrayList<>();
        LanguageDto languageDto = new LanguageDto();
        languageDto.setValue("zh");
        languageDto.setName("中文");
        languages.add(languageDto);
        languageDto = new LanguageDto();
        languageDto.setValue("ja");
        languageDto.setName("日本語");
        languages.add(languageDto);
        languageDto = new LanguageDto();
        languageDto.setValue("en");
        languageDto.setName("English");
        languages.add(languageDto);

        model.addAttribute("languages", languages);
        model.addAttribute("lang", messagesUtil.getLocale().getLanguage());
        localeResolver.setLocale(req, res, messagesUtil.getLocale());

        return "login";
    }

    /**
     * user login
     */
    @PostMapping("/login")
    @ResponseBody
    public LoginForm login(@RequestBody LoginForm form) {

        String password = CommonUtils.encrypt(form.getPassword());
        AdminUserMasterDto adminUserMaster = loginService.getAdminUserMaster(form.getMailAddress());
        String passwordDB = adminUserMaster.getSecUserPassword();

        if (!StringUtils.isEmpty(password) && password.equals(passwordDB)) {
            form.setReturnCode(Constants.RETURN_CODE_OK);
        }

        Map<Integer, Product> permissionMap = loginService.getUserPermission(adminUserMaster.getAdminUserId());

        // TODO FL
        form.setUsername(adminUserMaster.getSecUserName());
        System.out.println(permissionMap);

        return form;
    }

}
