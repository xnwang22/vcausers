package org.nbme.vca.users.services.impl;

import com.google.common.collect.ImmutableMap;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import org.nbme.vca.users.services.AdTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by rwang on 8/18/2016.
 */
@Service
public class AdTokenServiceImpl implements AdTokenService{
    @Autowired
    private ImmutableMap<String, String> activeDirectoryConfig;
    private String authority = "https://login.windows.net/";
    @Override
    @Cacheable("token")
    public String getToken() throws Throwable {
        AuthenticationResult result = getAccessTokenFromClientCredentials();
           return result.getAccessToken();

    }
    private AuthenticationResult getAccessTokenFromClientCredentials()
            throws Throwable {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        String tenant = activeDirectoryConfig.get("TENANT");
        String clientId = activeDirectoryConfig.get("CLIENT_ID");
        String clientSecret = activeDirectoryConfig.get("SECRET_KEY");
        String hostUrl = activeDirectoryConfig.get("GRAPH_HOST");
        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(authority + tenant + "/", true, service);
            Future<AuthenticationResult> future = context.acquireToken(
                    hostUrl, new ClientCredential(clientId,
                            clientSecret), null);
            result = future.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        } finally {
            service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }
}
