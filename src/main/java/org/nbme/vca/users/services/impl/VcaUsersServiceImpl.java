package org.nbme.vca.users.services.impl;

import com.google.common.collect.ImmutableMap;
import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.services.VcaUserService;
import org.nbme.vca.users.services.VcaUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rwang on 8/17/2016.
 */
@Service
public class VcaUsersServiceImpl implements VcaUsersService {
    private static Logger logger = LoggerFactory.getLogger(VcaUsersServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ImmutableMap<String, String> activeDirectoryConfig;

    @Autowired
    VcaUserService vcaUserService;

   Map<String, AdUser> userMap = new ConcurrentHashMap<>();


    @Override
    @CacheEvict(cacheNames = {"userMapCache"}, allEntries = true)
    public Map<String, AdUser> getUserMap() {
        if(userMap.isEmpty()) {
            List<AdUser> users = vcaUserService.getUsers();
            users.stream().forEach(i -> userMap.put(i.getDisplayName(), i));

        }
        return userMap;
    }

    @Override
    @Cacheable(sync = true, cacheNames = {"userPrincipleCache"})
    public String getUserPrinciple(String userName) {
        return userMap.get(userName).getUserPrincipalName();
    }

    @Override
    @Cacheable(sync = true, cacheNames = {"userOidCache"})
    public String getUserOid(String userName) {
        if(userMap.containsKey(userName))
            return userMap.get(userName).getObjectId();
        else {
            userMap = getUserMap();
            if(!userMap.containsKey(userName))
            {
                logger.warn("User {} not found", userName);
                throw new RuntimeException("user not found: " + userName);
            }
            return getUserOid(userName);
        }
    }
}
