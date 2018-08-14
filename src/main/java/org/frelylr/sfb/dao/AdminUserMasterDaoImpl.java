package org.frelylr.sfb.dao;

import java.util.HashMap;
import java.util.Map;

import org.frelylr.sfb.entity.AdminUserMaster;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserMasterDaoImpl extends BaseDao implements AdminUserMasterDao {

    /**
     * get admin user information
     */
    public AdminUserMaster selectAdminUserMasterByMail(String secUserMailAddress) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("   admin_user_id, ");
        sql.append("   country_id, ");
        sql.append("   sec_user_mail_address, ");
        sql.append("   sec_user_password, ");
        sql.append("   sec_user_name, ");
        sql.append("   remarks ");
        sql.append(" FROM ");
        sql.append("    core_master_db.admin_user_master ");
        sql.append(" WHERE ");
        sql.append("   sec_user_mail_address = :secUserMailAddress ");
        sql.append("   AND soft_delete_flag = 'open' ");

        Map<String, Object> params = new HashMap<>();
        params.put("secUserMailAddress", secUserMailAddress);

        return this.selectForEntity(sql, params, AdminUserMaster.class);
    }

}
