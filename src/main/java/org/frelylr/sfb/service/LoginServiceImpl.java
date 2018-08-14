package org.frelylr.sfb.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.frelylr.sfb.common.CommonUtils;
import org.frelylr.sfb.dao.AdminUserMasterDao;
import org.frelylr.sfb.dao.RelayAdminRoleEnvConsoleDao;
import org.frelylr.sfb.dto.AdminUserMasterDto;
import org.frelylr.sfb.entity.AdminEnvConsole;
import org.frelylr.sfb.entity.AdminUserMaster;
import org.frelylr.sfb.entity.RelayAdminRoleEnvConsole;
import org.frelylr.sfb.session.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AdminUserMasterDao adminUserMasterDao;

    @Autowired
    RelayAdminRoleEnvConsoleDao relayAdminRoleEnvConsoleDao;

    /**
     * get the admin user information
     */
    public AdminUserMasterDto getAdminUserMaster(String secUserMailAddress) {

        AdminUserMaster adminUserMaster = adminUserMasterDao.selectAdminUserMasterByMail(secUserMailAddress);
        AdminUserMasterDto adminUserMasterDto = new AdminUserMasterDto();
        try {
            adminUserMasterDto.setAdminUserId(adminUserMaster.getAdminUserId());
            adminUserMasterDto.setSecUserName(CommonUtils.byteToString(adminUserMaster.getSecUserName()));
            adminUserMasterDto.setSecUserPassword(CommonUtils.byteToString(adminUserMaster.getSecUserPassword()));
        } catch (Exception e) {
            adminUserMasterDto.setSecUserName(null);
            adminUserMasterDto.setSecUserPassword(null);
        }

        return adminUserMasterDto;
    }

    /**
     * get the user permission
     */
    public Map<Integer, Product> getUserPermission(Integer adminUserId) {

        List<RelayAdminRoleEnvConsole> permissionList = relayAdminRoleEnvConsoleDao.selectUserPermission(adminUserId);
        // Map<Integer, Product> productMap = permissionList.stream()
        // .collect(Collectors.toMap(RelayAdminRoleEnvConsole::getProductId, rarec -> {
        // Product product = new Product();
        // product.setUserPermission(CommonUtils.calculatePermission(rarec.getOperationType()));
        // AdminEnvConsole adminEnvConsole = new AdminEnvConsole();
        // adminEnvConsole.setAdminEnvId(rarec.getAdminEnvId());
        // adminEnvConsole.setAdminEnvName(rarec.getAdminEnvName());
        // product.getAdminEnvConsoleList().add(adminEnvConsole);
        // return product;
        // }));

        Map<Integer, List<AdminEnvConsole>> map = permissionList.stream()
                .collect(Collectors.groupingBy(RelayAdminRoleEnvConsole::getProductId, Collectors.mapping(rarec -> {
                    AdminEnvConsole adminEnvConsole = new AdminEnvConsole();
                    adminEnvConsole.setAdminEnvId(rarec.getAdminEnvId());
                    adminEnvConsole.setAdminEnvName(rarec.getAdminEnvName());
                    return adminEnvConsole;
                }, Collectors.toList())));

        map.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v);
        });

        return null;
    }
}
