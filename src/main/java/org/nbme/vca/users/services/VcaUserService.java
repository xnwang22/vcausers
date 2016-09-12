package org.nbme.vca.users.services;

import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.model.VcaUser;
import org.nbme.vca.users.model.VcaUserRole;

import java.util.List;

/**
 * Created by rwang on 8/17/2016.
 */
public interface VcaUserService {
    AdUser addUser(VcaUser user);
    void deleteUser(String userName);
    AdUser updateUser(VcaUser user);
    void updateUserRole(VcaUser vcaUser, VcaUserRole vcaUserRole);

    AdUser getUserInfo(String userName);
    List<AdUser> getUsers();
}
