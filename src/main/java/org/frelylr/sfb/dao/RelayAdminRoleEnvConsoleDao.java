package org.frelylr.sfb.dao;

import java.util.List;

import org.frelylr.sfb.entity.RelayAdminRoleEnvConsole;

public interface RelayAdminRoleEnvConsoleDao {

    /**
     * ユーザー権限を取得
     */
    List<RelayAdminRoleEnvConsole> selectUserPermission(Integer adminUserId);
}
