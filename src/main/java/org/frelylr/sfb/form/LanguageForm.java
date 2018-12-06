package org.frelylr.sfb.form;

import io.swagger.annotations.ApiModel;

@ApiModel("LanguageForm")
public class LanguageForm extends BaseForm {

    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
