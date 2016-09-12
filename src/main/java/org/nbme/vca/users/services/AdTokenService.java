package org.nbme.vca.users.services;

import com.microsoft.aad.adal4j.AuthenticationResult;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by rwang on 8/18/2016.
 */
public interface AdTokenService {
    String getToken() throws Throwable;

    @Cacheable("token")
    AuthenticationResult getAccessTokenFromClientCredentials()
            throws Throwable;
}
