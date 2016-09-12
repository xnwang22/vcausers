package org.nbme.vca.users.controller;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import org.nbme.vca.users.model.AdUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by rwang on 8/12/2016.
 */
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final static String AUTHORITY = "https://login.microsoftonline.com/common/";
    private final static String CLIENT_ID = "b9661f75-6a84-4049-a041-ce0377c37f5c";//"c3d4c489-9be6-4a8d-a5a7-6fc47ca3d2c5";
    private final static String USERNAME = "rwang@rwangtempnbme.onmicrosoft.com";
    private final static String PASSWORD = "Nbme123$";
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getToken(HttpServletRequest request, Model model) throws Exception {
        {

            String token = getAccessTokenFromRest();//getAccessTokenFromUserCredentials(USERNAME, PASSWORD);
            return new ResponseEntity(token, HttpStatus.OK);
        }
    }

    private static AuthenticationResult getAccessTokenFromUserCredentials(
            String username, String password) throws Exception {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    "https://rwangtempnbme.onmicrosoft.com/VCA_rest_api", CLIENT_ID, username, password, null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }
   /* @RequestMapping(value = "/user", method = RequestMethod.POST, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserInfo(HttpServletRequest request, Model model) throws Exception {
        {
            AuthenticationResult authenticationResult=null;

            AdUser user = getUser(authenticationResult);
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }*/
    String getAccessTokenFromRest()
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
            logger.error(e.getResponseBodyAsString());
            throw e;
        }
    }

    private AdUser getUser(AuthenticationResult authenticationResult) {
        return new AdUser();
    }
}
