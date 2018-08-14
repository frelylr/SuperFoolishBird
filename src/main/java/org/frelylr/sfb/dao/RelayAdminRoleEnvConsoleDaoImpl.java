package org.frelylr.sfb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.frelylr.sfb.entity.RelayAdminRoleEnvConsole;
import org.springframework.stereotype.Repository;

@Repository
public class RelayAdminRoleEnvConsoleDaoImpl extends BaseDao implements RelayAdminRoleEnvConsoleDao {

    /**
     * get admin user permission
     */
    public List<RelayAdminRoleEnvConsole> selectUserPermission(Integer adminUserId) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT");
        sql.append("   rarec.product_id, ");
        sql.append("   rarec.operation_type, ");
        sql.append("   aec.admin_env_id, ");
        sql.append("   aec.admin_env_name ");
        sql.append(" FROM ");
        sql.append("   relay_admin_role_env_console rarec ");
        sql.append(" INNER JOIN ");
        sql.append("   admin_env_console aec ");
        sql.append(" ON ");
        sql.append("   rarec.product_id =  aec.product_id ");
        sql.append(" AND ");
        sql.append("   rarec.admin_env_id =  aec.admin_env_id ");
        sql.append(" INNER JOIN ");
        sql.append("   relay_admin_user_product raup ");
        sql.append(" ON ");
        sql.append("   rarec.product_id = raup.product_id ");
        sql.append(" AND ");
        sql.append("   rarec.admin_role_id = raup.admin_role_id ");
        sql.append(" INNER JOIN ");
        sql.append("   admin_role ar ");
        sql.append(" ON ");
        sql.append("   rarec.product_id = ar.product_id ");
        sql.append(" AND ");
        sql.append("   rarec.admin_role_id = ar.admin_role_id ");
        sql.append(" WHERE ");
        sql.append("   raup.admin_user_id = :adminUserId ");

        Map<String, Object> params = new HashMap<>();
        params.put("adminUserId", adminUserId);

        return this.selectForEntityList(sql, params, RelayAdminRoleEnvConsole.class);
    }

}
