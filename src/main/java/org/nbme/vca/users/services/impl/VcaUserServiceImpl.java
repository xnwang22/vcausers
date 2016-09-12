package org.nbme.vca.users.services.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.nbme.vca.users.config.AdConfig;
import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.model.Vca2AdUser;
import org.nbme.vca.users.model.VcaUser;
import org.nbme.vca.users.model.VcaUserRole;
import org.nbme.vca.users.repo.VcaUserRepo;
import org.nbme.vca.users.services.HttpHeadersService;
import org.nbme.vca.users.services.VcaUserService;
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

/**
 * Created by rwang on 8/17/2016.
 */
@Service
public class VcaUserServiceImpl implements VcaUserService {
    private static Logger logger = LoggerFactory.getLogger(VcaUserServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AdConfig activeDirectoryConfig;
    @Autowired
    HttpHeadersService httpHeadersService;
    @Autowired
    VcaUsersService vcaUsersService;
    @Autowired
    VcaUserRepo vcaUserRepo;

  /*  private RestOperation<VcaUser, AdUser>  createUserOperation = (String url, HttpMethod method, HttpEntity<VcaUser> httpEntity) ->
    {ResponseEntity<AdUser> responseEntity = restTemplate.postForEntity(url, httpEntity, AdUser.class); return responseEntity.getBody();};*/
    // get users Rest operation
    private RestOperation<String, List<AdUser>> getUsersOperation = (String url, HttpMethod method, HttpEntity<String> httpEntity) ->
    {List<AdUser> responses = restTemplate.exchange(url, method, httpEntity, AdUserResponse.class).getBody().getUsers(); return responses;};

    static class AdUserResponse {
        @JsonProperty("odata.metadata")
        private String odataMetadata;
        @JsonProperty("value")
        private List<AdUser> users;

        public List<AdUser> getUsers() {
            return users;
        }

        public void setUsers(List<AdUser> users) {
            this.users = users;
        }

        public String getOdataMetadata() {
            return odataMetadata;
        }

        public void setOdataMetadata(String odataMetadata) {
            this.odataMetadata = odataMetadata;
        }
    }

    @Override
    public AdUser addUser(Vca2AdUser user) {
        RestOperation<Vca2AdUser, AdUser>  createUserOperation = (String url, HttpMethod method, HttpEntity<Vca2AdUser> httpEntity) ->
        {ResponseEntity<AdUser> responseEntity = restTemplate.postForEntity(url, httpEntity, AdUser.class); return responseEntity.getBody();};

        HttpHeaders headers = httpHeadersService.createHttpHeaders();
        
        HttpEntity<Vca2AdUser> httpEntity = new HttpEntity<>(user, headers);
        String url = activeDirectoryConfig.getUserEp();
        logger.debug("user url ={}", url);
        try {
            return RestOperation.invoke(url,  HttpMethod.POST, httpEntity, createUserOperation);//restTemplate.postForEntity(url,  httpEntity, AdUser.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }

    }
    

    @Override
    public void deleteUser(String userName) {
        HttpHeaders headers = httpHeadersService.createHttpHeaders();
        String url = activeDirectoryConfig.getUserEp();
        url = url.replace("?api-version=1.6", "/" + userName + "?api-version=1.6");
        logger.debug("user url = {}", url);
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, String.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }
    }

    @Override
    public AdUser updateUser(Vca2AdUser user) {
        HttpHeaders headers = httpHeadersService.createHttpHeaders();

        HttpEntity<Vca2AdUser> httpEntity = new HttpEntity<>(user, headers);
        String url = activeDirectoryConfig.getUserEp();
        
        url = url.replace("?api-version=1.6", "/" + vcaUsersService.getUserPrinciple(user.getDisplayName()) + "?api-version=1.6"); // TO add user principle
        logger.debug("user url ={}", url);
        try {
            ResponseEntity<AdUser> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, AdUser.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }

    }

    @Override
    public AdUser patchUser(String userName, String patchJsonString) {
        HttpHeaders headers = httpHeadersService.createHttpHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>(patchJsonString, headers);
        String url = activeDirectoryConfig.getUserEp();
        url = url.replace("?api-version=1.6", "/" + vcaUsersService.getUserPrinciple(userName) + "?api-version=1.6"); // TO add user principle
        logger.debug("user url ={}", url);
        try {
            ResponseEntity<AdUser> response = restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, AdUser.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }

    }
    @Override
    public List<AdUser> getUsers() {

        HttpHeaders headers = httpHeadersService.createHttpHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
        String url = activeDirectoryConfig.getUserEp();

        logger.debug("user url ={}", url);
        try {
            return RestOperation.invoke(url,  HttpMethod.GET, httpEntity, getUsersOperation);//restTemplate.postForEntity(url,  httpEntity, AdUser.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }

    }

    @Override
    public void updateUserRole(Vca2AdUser vcaUser, VcaUserRole vcaUserRole) {

    }

    @Override
    public AdUser getUserInfo(String userName) {
        HttpHeaders headers = httpHeadersService.createHttpHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
        String url = activeDirectoryConfig.getUserEp();
        VcaUser vcaUser = vcaUserRepo.findByUName(userName);
        if(vcaUser == null)
            throw new IllegalArgumentException(userName + " not found");

        url = url.replace("?api-version=1.6", "/" + vcaUser.getAdUser() + "?api-version=1.6");
        logger.debug("user url ={}", url);
        try {
            ResponseEntity<AdUser> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AdUser.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }
    }

}
