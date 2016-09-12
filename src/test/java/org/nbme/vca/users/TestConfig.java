package org.nbme.vca.users;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by rwang on 8/15/2016.
 */
@Configuration
@Profile("test")
@ComponentScan(basePackages = {"org.nbme.vca.users.services"})
public class TestConfig {
    @Bean
    public RestTemplate restTemplate()
    {

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials( new AuthScope("sg1.nbme.org", 80), new UsernamePasswordCredentials("rwang", "RMB$250xxn") );
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.useSystemProperties();
        clientBuilder.setProxy(new HttpHost("sg1.nbme.org", 80));
        clientBuilder.setDefaultCredentialsProvider(credsProvider);
        clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

        CloseableHttpClient client = clientBuilder.build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(client);

        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

}
