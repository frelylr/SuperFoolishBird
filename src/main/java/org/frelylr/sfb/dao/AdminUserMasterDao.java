package org.frelylr.sfb.dao;

import org.frelylr.sfb.entity.AdminUserMaster;

public interface AdminUserMasterDao {

    /**
     * get admin user information
     */
    AdminUserMaster selectAdminUserMasterByMail(String secUserMailAddress);
}
