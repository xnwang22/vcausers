package org.nbme.vca.users.services.impl;


import org.nbme.vca.users.services.AdTokenService;
import org.nbme.vca.users.services.HttpHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * Created by rwang on 8/23/2016.
 */
@Service
public class HttpHeaderServiceImpl implements HttpHeadersService{
    @Autowired
    private AdTokenService adTokenService;

    @Override
    public HttpHeaders createHttpHeaders() {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = null;
        try {
            token = adTokenService.getToken();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("Failed to get token");
        }
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }
}
