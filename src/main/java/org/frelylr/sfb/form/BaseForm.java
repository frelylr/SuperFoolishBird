package org.frelylr.sfb.form;

import org.frelylr.sfb.common.Constants;

import io.swagger.annotations.ApiModel;

public class BaseForm {

    private Integer returnCode = Constants.RETURN_CODE_OK;

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

}
