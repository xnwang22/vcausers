package org.nbme.vca.users.services;

import org.nbme.vca.users.model.AdUserRole;
import org.nbme.vca.users.model.VcaUserRole;
import org.springframework.cache.annotation.Cacheable;

import java.util.EnumMap;
import java.util.List;

/**
 * Created by rwang on 8/17/2016.
 */
public interface VcaUserRolesService {
    List<AdUserRole> getRoles();
    EnumMap<VcaUserRole, AdUserRole> getUserRoleMap();

//    @Cacheable(key = "", sync = true, cacheNames = {"userRoleCache"})
    String getUserRoleOid(String roleName);
}
