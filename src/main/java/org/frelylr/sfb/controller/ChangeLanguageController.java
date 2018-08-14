package org.frelylr.sfb.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.frelylr.sfb.common.Constants;
import org.frelylr.sfb.common.MessagesUtil;
import org.frelylr.sfb.form.BaseForm;
import org.frelylr.sfb.form.LanguageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

@RestController
public class ChangeLanguageController {

    @Autowired
    private MessagesUtil messagesUtil;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * change the language
     */
    @RequestMapping(value = "/changeLanguage")
    public BaseForm changeLanguage(@RequestBody LanguageForm form, HttpServletRequest req, HttpServletResponse res) {

        switch (form.getLang()) {
            case Constants.JA:
                setLocalLanguage(req, res, Locale.JAPAN);
                break;
            case Constants.ZH:
                setLocalLanguage(req, res, Locale.CHINA);
                break;
            case Constants.EN:
                setLocalLanguage(req, res, Locale.US);
                break;
            default:
                setLocalLanguage(req, res, Locale.JAPAN);
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
