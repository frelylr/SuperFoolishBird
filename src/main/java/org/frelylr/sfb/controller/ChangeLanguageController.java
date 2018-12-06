package org.frelylr.sfb.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.frelylr.sfb.common.Constants;
import org.frelylr.sfb.common.MessagesUtils;
import org.frelylr.sfb.form.BaseForm;
import org.frelylr.sfb.form.LanguageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "选择语言")
@RestController
public class ChangeLanguageController {

    @Autowired
    private MessagesUtils messagesUtil;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * change the language
     */
    @ApiOperation("改变语言")
    @RequestMapping(value = "/changeLanguage")
    public BaseForm changeLanguage(@RequestBody LanguageForm form, HttpServletRequest req, HttpServletResponse res) {

        switch (form.getLang()) {
            case Constants.ZH:
                setLocalLanguage(req, res, Locale.CHINA);
                break;
            case Constants.EN:
                setLocalLanguage(req, res, Locale.US);
                break;
            case Constants.JA:
                setLocalLanguage(req, res, Locale.JAPAN);
                break;
            default:
                setLocalLanguage(req, res, Locale.CHINA);
        }

        return form;
    }

    /**
     * set the local language
     */
    private void setLocalLanguage(HttpServletRequest req, HttpServletResponse res, Locale locale) {

        messagesUtil.setLocale(locale);
        localeResolver.setLocale(req, res, locale);
    }

}
