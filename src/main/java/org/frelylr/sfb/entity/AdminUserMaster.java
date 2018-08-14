package org.frelylr.sfb.entity;

public class AdminUserMaster {

    private Integer adminUserId;
    private Integer countryId;
    private byte[] secUserMailAddress;
    private byte[] secUserPassword;
    private byte[] secUserName;
    private byte[] remarks;
    private String softDeleteFlag;

    /**
     * soft_delete_flag enum
     */
    public enum SoftDeleteFlag {
        open, deleted
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public byte[] getSecUserMailAddress() {
        return secUserMailAddress;
    }

    public void setSecUserMailAddress(byte[] secUserMailAddress) {
        this.secUserMailAddress = secUserMailAddress;
    }

    public byte[] getSecUserPassword() {
        return secUserPassword;
    }

    public void setSecUserPassword(byte[] secUserPassword) {
        this.secUserPassword = secUserPassword;
    }

    public byte[] getSecUserName() {
        return secUserName;
    }

    public void setSecUserName(byte[] secUserName) {
        this.secUserName = secUserName;
    }

    public byte[] getRemarks() {
        return remarks;
    }

    public void setRemarks(byte[] remarks) {
        this.remarks = remarks;
    }

    public String getSoftDeleteFlag() {
        return softDeleteFlag;
    }

    public void setSoftDeleteFlag(String softDeleteFlag) {
        this.softDeleteFlag = softDeleteFlag;
    }

}
