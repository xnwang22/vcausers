package org.nbme.vca.users.services;

import org.nbme.vca.users.model.VcaUserRole;

import java.util.List;

/**
 * Created by rwang on 8/17/2016.
 */
public interface VcaUserRoleService {
    String addUserRole(String userName, String vcaUserRole);
    void deleteUserRole(String userName, String vcaUserRole);
    VcaUserRole getUserRole(String userName);
    List<VcaUserRole> getUserRoles(String userName);

    void updateUserRole(String userName, String fromGroupName, String toGroupName);
}
