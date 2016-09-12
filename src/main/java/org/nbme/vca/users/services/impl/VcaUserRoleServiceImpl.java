package org.nbme.vca.users.services.impl;

import com.google.common.collect.ImmutableMap;
import org.nbme.vca.users.model.AdUserRole;
import org.nbme.vca.users.model.AdUserRolesResponse;
import org.nbme.vca.users.model.UrlObject;
import org.nbme.vca.users.model.VcaUserRole;
import org.nbme.vca.users.services.HttpHeadersService;
import org.nbme.vca.users.services.VcaUserRoleService;
import org.nbme.vca.users.services.VcaUserRolesService;
import org.nbme.vca.users.services.VcaUsersService;
import org.nbme.vca.users.utils.RestOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rwang on 8/17/2016.
 */
@Service
public class VcaUserRoleServiceImpl implements VcaUserRoleService {
    private static Logger logger = LoggerFactory.getLogger(VcaUserRoleServiceImpl.class);
    @Autowired
//    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private ImmutableMap<String, String> activeDirectoryConfig;
    @Autowired
    private HttpHeadersService httpHeadersService;
    @Autowired
    private VcaUserRolesService vcaUserRolesService;
    @Autowired
    private VcaUsersService vcaUsersService;
    // REST operations
    private RestOperation<UrlObject, String> addUserRoleOperation = (String url, HttpMethod method, HttpEntity<UrlObject> httpEntity) ->
    {ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class); return responseEntity.getBody();};
    // delete
    RestOperation<String, String> deleteUserRoleOperation = (String url, HttpMethod method, HttpEntity<String> httpEntity) ->
    {ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, httpEntity, String.class); return responseEntity.getBody();};
    // get
    RestOperation<String, List<AdUserRole>> getUserRoleOperation = (String url, HttpMethod method, HttpEntity<String> httpEntity) ->
    {List<AdUserRole> responseEntity = restTemplate.exchange(url, method, httpEntity, AdUserRolesResponse.class).getBody().getAdUserRoles(); return responseEntity;};

    @Override
    public String addUserRole(String userName, String vcaUserRole) {

            HttpHeaders headers = httpHeadersService.createHttpHeaders();
            String userOid = vcaUsersService.getUserOid(userName);
            HttpEntity<UrlObject> httpEntity = new HttpEntity<>(new UrlObject(userOid), headers); //rwang, "6c848272-8e3e-405a-9ca6-13bb61b4ba9a"
            String url = activeDirectoryConfig.get("GROUP_EP");

            String groupOid = vcaUserRolesService.getUserRoleOid(vcaUserRole);
//            String groupId = "bad868ab-18ed-44de-a53b-a91db903a99f"; //participant
            url = url.replace("?api-version=1.6", "/" + groupOid + "/$links/members?api-version=1.6");
            logger.debug("user url ={}", url);
            try {

                String result = RestOperation.invoke(url, HttpMethod.POST, httpEntity, addUserRoleOperation);
                logger.debug("return string = {}", result );
                return result;

            } catch (HttpClientErrorException | HttpServerErrorException e) {
                logger.error(e.getResponseBodyAsString());
                throw e;
            }

        }

        @Override
        public void deleteUserRole(String userName, String vcaUserRole) {
            HttpHeaders headers = httpHeadersService.createHttpHeaders();
            String userOid = vcaUsersService.getUserOid(userName);
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
            String url = activeDirectoryConfig.get("GROUP_EP");

            String groupOid = vcaUserRolesService.getUserRoleOid(vcaUserRole);

            url = url.replace("?api-version=1.6", "/" + groupOid + "/$links/members/" + userOid + "?api-version=1.6");
            logger.debug("user url ={}", url);
            try {

                String result = RestOperation.invoke(url, HttpMethod.DELETE, httpEntity, deleteUserRoleOperation);
                logger.debug("return string = {}", result );
                return ;

            } catch (HttpClientErrorException | HttpServerErrorException e) {
                logger.error(e.getResponseBodyAsString());
                throw e;
            }

        }

        @Override
        public VcaUserRole getUserRole(String userName) {
            List<VcaUserRole> userRoles = getUserRoles(userName);
            return userRoles.stream().sorted((t0, t1) -> t1.getLevel() - t0.getLevel()).findFirst().get();
        }

        @Override
        public List<VcaUserRole> getUserRoles(String userName) {
            HttpHeaders headers = httpHeadersService.createHttpHeaders();
            String userOid = vcaUsersService.getUserOid(userName);
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
            String url = activeDirectoryConfig.get("USER_EP");
            url = url.replace("?api-version=1.6", "/" + userOid + "/memberOf?api-version=1.6");
            logger.debug("user url ={}", url);
            try {
                List<AdUserRole> result = RestOperation.invoke(url, HttpMethod.GET, httpEntity, getUserRoleOperation);
                logger.debug("return string = {}", result );

                return result.stream().map(e -> VcaUserRole.valueOf(e.getDisplayName().toUpperCase())).collect(Collectors.toList());

            } catch (HttpClientErrorException | HttpServerErrorException e) {
                logger.error(e.getResponseBodyAsString());
                throw e;
            }
        }

    @Override
    public void updateUserRole(String userName, String fromGroupName, String toGroupName) {
        deleteUserRole(userName, fromGroupName);
        addUserRole(userName, toGroupName);
    }
}
