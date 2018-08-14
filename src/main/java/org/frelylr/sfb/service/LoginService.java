package org.frelylr.sfb.service;

import org.frelylr.sfb.dto.AdminUserMasterDto;
import org.frelylr.sfb.session.Product;

import java.util.Map;

public interface LoginService {

    /**
     * get the admin user information
     */
    AdminUserMasterDto getAdminUserMaster(String secUserMailAddress);

    /**
     * get the user permission
     */
    Map<Integer, Product> getUserPermission(Integer adminUserId);

}
