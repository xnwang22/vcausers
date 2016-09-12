package org.nbme.vca.users.security.service;


import org.nbme.vca.users.security.model.LoginKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rwang on 8/30/2016.
 */
@Service
public class LoginKeyServiceImpl implements LoginKeyService {
    private static String serviceUrl = "https://login.microsoftonline.com/common/discovery/keys";
    private static Logger logger = LoggerFactory.getLogger(LoginKeyServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Override
    @Cacheable( cacheNames = {"msLoginKey"})
    public String getKey() {
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-type", "application/json");
        try {


            LoginKey loginKey = restTemplate.exchange(serviceUrl, HttpMethod.GET, new HttpEntity<>("", httpHeaders), LoginKey.class).getBody();

            return loginKey.getKeys().get(1).getX5c().get(0);//.stream().findFirst().get().getX5c().stream().findFirst().get(); //reduce((first, second) -> second).get().;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error(e.getResponseBodyAsString());
            throw e;
        }
    }
}
