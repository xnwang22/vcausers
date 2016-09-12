package org.nbme.vca.users.controller;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import org.nbme.vca.users.config.AdConfig;
import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.utils.AuthHelper;
import org.nbme.vca.users.utils.UserConstants;
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
 * 
 * This controller is to autheticate users and return tokens from Azure ad.
 */
@RestController
@RequestMapping(value = "/validate")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    //CONST
    private final static String AUTHORITY = "https://login.microsoftonline.com/common/";
    
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    AdConfig adConfig;

    /**
     * This method expects an authorization header that is basic.
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getToken(HttpServletRequest request, Model model) throws Exception {
        {
            String[] creds = AuthHelper.processBasicHeader(request);
            if(creds.length != 2){
            	return new ResponseEntity("There was not a proper authentication header", HttpStatus.UNAUTHORIZED);
            }
            String token = getAccessTokenFromRest(creds[0],creds[1]);
        	
        	return new ResponseEntity(token, HttpStatus.OK);
        }
    }

    /**
     * 
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    private AuthenticationResult getAccessTokenFromUserCredentials(String username, String password) throws Exception {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    "https://rwangtempnbme.onmicrosoft.com/VCA_rest_api", adConfig.getClientId() , username, password, null);
            result = future.get();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }
        return result;
    }
    
//    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity getUserInfo(HttpServletRequest request, Model model) throws Exception {
//        {
//            AuthenticationResult authenticationResult=null;
//            AdUser user = getUser(authenticationResult);
//            return new ResponseEntity(user, HttpStatus.OK);
//        }
//    }
    /**
     * This method will use a rest template to make the request for the token
     * @return
     */
    private String getAccessTokenFromRest(String userName, String password)
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
            logger.error(e.getResponseBodyAsString());
            throw e;
        }
    }

    private AdUser getUser(AuthenticationResult authenticationResult) {
        return new AdUser();
    }
    
    
    
    
    
//    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<String> auth(ServletRequest request, ServletResponse response)
//    {
//
//    	ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
//        if (request instanceof HttpServletRequest) {
//            HttpServletRequest httpRequest = (HttpServletRequest) request;
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            try {
//
//            	if (AuthHelper.containsAuthenticationData(httpRequest) || true) 
//            	{
//                    
//                    Map<String,String> params = this.addParams("", "");
//                    
//                    AuthenticationResponse authResponse = 
//                    		AuthenticationResponseParser.parse(new URI(adConfig.getOAuthUrl()), params);
//                    logger.debug("LOGIN SUCCESS?" + AuthHelper.isAuthenticationSuccessful(authResponse));
//                    AuthenticationSuccessResponse success =  (AuthenticationSuccessResponse) authResponse;
//                    
//                    logger.debug("LOGIN SUCCESS?" + AuthHelper.isAuthenticationSuccessful(authResponse));
//                    
//                   if (AuthHelper.isAuthenticationSuccessful(authResponse)) {
//
//                        AuthenticationSuccessResponse oidcResponse = (AuthenticationSuccessResponse) authResponse;
//                        try {
//							AuthenticationResult result = getAccessToken(
//							        oidcResponse.getAuthorizationCode(),
//							        "");
//						} catch (Throwable e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
////                        createSessionPrincipal(httpRequest, result);
//                    } else {
//                        AuthenticationErrorResponse oidcResponse = (AuthenticationErrorResponse) authResponse;
//                        throw new Exception(String.format(
//                                "Request for auth code failed: %s - %s",
//                                oidcResponse.getErrorObject().getCode(),
//                                oidcResponse.getErrorObject()
//                                        .getDescription()));
//                    }
//                    
//            	}
//            }
//        	catch(Exception e){
//        		logger.error("", e);
//        	}
//            
//            
//        }
//		return responseEntity;
//    }
//    
//    
//    private AuthenticationResult getAccessToken( AuthorizationCode authorizationCode, String currentUri) throws Throwable 
//    {
//        String authCode = authorizationCode.getValue();
//        ClientCredential credential = new ClientCredential(adConfig.getClientId(),adConfig.getSecretKey());
//        AuthenticationContext context = null;
//        AuthenticationResult result = null;
//        ExecutorService service = null;
//        try {
//            service = Executors.newFixedThreadPool(1);
//            context = new AuthenticationContext("https://login.windows.net/" + adConfig.getTenantId()+"/", true, service);
//            Future<AuthenticationResult> future = context.acquireTokenByAuthorizationCode(authCode, new URI( "vca:\\all"), credential, null);
//            result = future.get();
//        } catch (ExecutionException e) {
//            throw e.getCause();
//        } finally {
//            service.shutdown();
//        }
//
//        if (result == null) {
//            throw new ServiceUnavailableException(
//                    "authentication result was null");
//        }
//        return result;
//    }
    
}
