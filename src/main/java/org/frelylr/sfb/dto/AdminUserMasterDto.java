package org.frelylr.sfb.dto;

public class AdminUserMasterDto {

    private Integer adminUserId;
    private String secUserPassword;
    private String secUserName;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getSecUserPassword() {
        return secUserPassword;
    }

    public void setSecUserPassword(String secUserPassword) {
        this.secUserPassword = secUserPassword;
    }

    public String getSecUserName() {
        return secUserName;
    }

    public void setSecUserName(String secUserName) {
        this.secUserName = secUserName;
    }

}
