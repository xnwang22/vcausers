package org.nbme.vca.users.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rwang on 8/17/2016.
 */
public class RestUtil {
    @Autowired
    RestTemplate restTemplate;
    public String getAccessTokenFromRest()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add("Accept", "application/json");
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

//        bodyMap.add("Content-type", "application/xml; charset=utf-8");
        bodyMap.add("grant_type", "password");
        bodyMap.add("resource", "https://rwangtempnbme.onmicrosoft.com/VCA_rest_api");
        bodyMap.add("client_id", "b9661f75-6a84-4049-a041-ce0377c37f5c");
        bodyMap.add("username", "rwang@rwangtempnbme.onmicrosoft.com");
        bodyMap.add("password", "Nbme123$");
        bodyMap.add("scope", "read write");
        bodyMap.add("client_secret", "/TndLq2AYUacZ1xT/vJ9SujuMLPzKiq8Vgb24eFuG88=");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(bodyMap, headers);
        String url = "https://login.microsoftonline.com/04af9511-4e7b-4380-83de-1923a6735b4a/oauth2/token";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url,  httpEntity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            logger.error(e.getResponseBodyAsString());
            throw e;
        }
    }
}
