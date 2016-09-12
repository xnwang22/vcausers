package org.nbme.vca.users.IT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nbme.vca.users.TestConfig;
import org.nbme.vca.users.config.AdConfig;
import org.nbme.vca.users.utils.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rwang on 8/30/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { AdConfig.class, TestConfig.class})
public class GetTokenIT {
    @Autowired
    AdConfig adConfig;
    @Autowired
    RestTemplate restTemplate;
    @Test
    public void  testGetToken()
    {
        String token = getAccessTokenFromRest("User4", "Test1234");
        System.out.println(token);
    }
    String getAccessTokenFromRest(String userName, String password)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();

        bodyMap.add(UserConstants.HEADER_GRANT_TYE, "password");
        bodyMap.add(UserConstants.HEADER_SCOPE, "read write");

        //config vales
        bodyMap.add(UserConstants.HEADER_CLIENT_ID,adConfig.getClientId());
        bodyMap.add(UserConstants.HEADER_CLIENT_SECRET,adConfig.getSecretKey());
        bodyMap.add(UserConstants.HEADER_RESOURCE,adConfig.getResource());

        //user values
        bodyMap.add(UserConstants.HEADER_USERNAME, userName);
        bodyMap.add(UserConstants.HEADER_PASSWORD, password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(bodyMap, headers);
        String url = adConfig.getOAuthUrl();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url,  httpEntity, String.class);

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }
    }
}
