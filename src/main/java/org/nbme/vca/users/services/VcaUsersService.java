package org.nbme.vca.users.services;

import org.nbme.vca.users.model.AdUser;

import java.util.Map;

/**
 * Created by rwang on 8/22/2016.
 */
public interface VcaUsersService {
//    List<AdUser> getUsers();
    Map<String, AdUser> getUserMap();
    String getUserPrinciple(String userName);

    String getUserOid(String userName);
}
