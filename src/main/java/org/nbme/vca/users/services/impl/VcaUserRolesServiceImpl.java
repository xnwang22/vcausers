package org.nbme.vca.users.services.impl;

import com.google.common.collect.ImmutableMap;
import org.nbme.vca.users.model.AdUserRole;
import org.nbme.vca.users.model.AdUserRolesResponse;
import org.nbme.vca.users.model.VcaUserRole;
import org.nbme.vca.users.services.HttpHeadersService;
import org.nbme.vca.users.services.VcaUserRolesService;
import org.nbme.vca.users.utils.RestOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by rwang on 8/17/2016.
 */
@Service
public class VcaUserRolesServiceImpl implements VcaUserRolesService {
    private static Logger logger = LoggerFactory.getLogger(VcaUserRolesServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ImmutableMap<String, String> activeDirectoryConfig;
    @Autowired
    private HttpHeadersService httpHeadersService;

    private EnumMap<VcaUserRole, AdUserRole> userRoleMap = new EnumMap<>(VcaUserRole.class);


    @Override
    public List<AdUserRole> getRoles() {
        RestOperation<String, List<AdUserRole>> getRolesOperation = (String url, HttpMethod method, HttpEntity<String> httpEntity) ->
        {List<AdUserRole> responseEntity = restTemplate.exchange(url, method, httpEntity, AdUserRolesResponse.class).getBody().getAdUserRoles(); return responseEntity;};

        HttpHeaders headers = httpHeadersService.createHttpHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
        String url = activeDirectoryConfig.get("GROUP_EP");

        logger.debug("user url ={}", url);
        try {
            return RestOperation.invoke(url,  HttpMethod.GET, httpEntity, getRolesOperation);//restTemplate.postForEntity(url,  httpEntity, AdUser.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }

    }

    @Override
    @CacheEvict(key = "", cacheNames = {"userRoleCache"})
    public EnumMap<VcaUserRole, AdUserRole> getUserRoleMap() {
        userRoleMap.clear();
        List<AdUserRole> userRoles = getRoles();
        userRoles.stream().forEach(i->userRoleMap.put(VcaUserRole.valueOf(i.getDisplayName().toUpperCase()), i));
        return userRoleMap;
    }

    @Override
//    @Cacheable(key = "", sync = true, cacheNames = {"userRoleCache"})
    public String getUserRoleOid(String roleName) {
        VcaUserRole role = VcaUserRole.ADMIN.valueOf(roleName.toUpperCase());
        if(userRoleMap.containsKey(role))
            return userRoleMap.get(role).getObjectId();
        else {
            userRoleMap = getUserRoleMap();
            if(userRoleMap.containsKey(role))
                return getUserRoleOid(roleName);
            else {
                String message = MessageFormat.format("User role {0} is not configured!", roleName);
                logger.error(message);
                throw new IllegalArgumentException(message );
            }
        }

    }
}
