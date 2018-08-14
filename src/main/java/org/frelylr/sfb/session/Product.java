package org.frelylr.sfb.session;

import org.frelylr.sfb.entity.AdminEnvConsole;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private UserPermission userPermission;
    private List<AdminEnvConsole> adminEnvConsoleList = new ArrayList<>();

    public UserPermission getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(UserPermission userPermission) {
        this.userPermission = userPermission;
    }

    public List<AdminEnvConsole> getAdminEnvConsoleList() {
        return adminEnvConsoleList;
    }

    public void setAdminEnvConsoleList(List<AdminEnvConsole> adminEnvConsoleList) {
        this.adminEnvConsoleList = adminEnvConsoleList;
    }

}
