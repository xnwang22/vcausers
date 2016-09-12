package org.nbme.vca.users.config;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by rwang on 8/12/2016.
 */
@Configuration
/*@PropertySources({
        @PropertySource(value = "classpath:application${runtime.environment}.properties"),

})*/
public class AdConfig {

    private static final Logger log = LoggerFactory.getLogger(AdConfig.class);

    @Value("${vca.ad.tenant}")
    private String tenant;

    @Value("${vca.ad.secret_key}")
    private String secretKey;

    @Value("${vca.ad.userName}")
    private String userName;

    @Value("${vca.ad.password}")
    private String password;

    @Value("${vca.ad.client_id}")
    private String clientId;


    @Value("${vca.ad.graph.host}")
    private String graphHost;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        String activeProfile = System.getProperty("spring.profiles.active", "");
        String propertiesFilename = "application" + activeProfile + ".properties";

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource(propertiesFilename));

        return configurer;
    }

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

       /* RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate;*/
    }

    @Bean
    public ImmutableMap<String, String> activeDirectoryConfig() {
        String epFormat="%s/%s/%s?api-version=1.6";
        ImmutableMap<String, String> config = ImmutableMap.<String, String>builder()
                .put("TENANT", tenant)
                .put("SECRET_KEY", secretKey)
                .put("USER_NAME", userName)
                .put("PASSWORD", password)
                .put("CLIENT_ID", clientId)
                .put("GRAPH_HOST", graphHost)
                .put("USER_EP", String.format(epFormat, graphHost, "myorganization", "users"))
                .put("GROUP_EP", String.format(epFormat, graphHost, "myorganization", "groups"))
                .build();

        return config;
    }
}