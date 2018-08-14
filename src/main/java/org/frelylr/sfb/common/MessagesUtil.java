package org.frelylr.sfb.common;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MessagesUtil {

    @Autowired
    private MessageSource messageSource;

    private Locale locale = Locale.CHINA;

    /**
     * get the custom message without parameters
     */
    public String getMessage(String key) {

        return messageSource.getMessage(key, null, locale);
    }

    /**
     * get the custom message with parameters
     */
    public String getMessage(String key, @Nullable Object[] params) {

        return messageSource.getMessage(key, params, locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
