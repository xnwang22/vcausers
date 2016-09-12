package org.nbme.vca.users.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

/**
 * Created by rwang on 8/19/2016.
 */
public interface RestOperation<T, V> {
    V invoke(String url, HttpMethod method, HttpEntity<T> httpEntity);
    static <T,V> V invoke(String url, HttpMethod method, HttpEntity<T> httpEntity, RestOperation<T, V> op)
    {
        return op.invoke(url,  method, httpEntity);
    }
}

