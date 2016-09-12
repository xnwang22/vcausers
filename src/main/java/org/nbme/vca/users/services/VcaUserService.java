package org.nbme.vca.users.services;

import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.model.Vca2AdUser;
import org.nbme.vca.users.model.VcaUserRole;

import java.util.List;

/**
 * Created by rwang on 8/17/2016.
 */
public interface VcaUserService {
    AdUser addUser(Vca2AdUser user);
    void deleteUser(String userName);
    AdUser updateUser(Vca2AdUser user);
    void updateUserRole(Vca2AdUser vcaUser, VcaUserRole vcaUserRole);

    AdUser getUserInfo(String userName);

    AdUser patchUser(String userName, String patchJsonString);

    List<AdUser> getUsers();
}
