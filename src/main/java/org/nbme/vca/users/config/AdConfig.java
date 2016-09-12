package org.nbme.vca.users.config;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by rwang on 8/12/2016.
 */
@Configuration
/*
 * @PropertySources({
 * 
 * @PropertySource(value =
 * "classpath:application${runtime.environment}.properties"),
 * 
 * })
 */
public class AdConfig {

	private static final Logger log = LoggerFactory.getLogger(AdConfig.class);
	
	private static final String epFormat = "%s/%s/%s?api-version=1.6";

	// tenat config info
	@Value("${vca.ad.tenant}")
	private String tenant;
	@Value("${vca.ad.tenantId}")
	private String tenantId;
	// user we make the graph calls on behalf of
	@Value("${vca.ad.userName}")
	private String userName;
	@Value("${vca.ad.password}")
	private String password;

	// client config data
	@Value("${vca.ad.secret_key}")
	private String secretKey;
	@Value("${vca.ad.client_id}")
	private String clientId;

	@Value("${vca.ad.resource}")
	private String resource;

	// consts... or almost const.
	@Value("${vca.ad.graph.host}")
	private String graphHost;
	@Value("${vca.ad.authority}")
	private String authority;
	@Value("${vca.ad.issuer.domain}")
	private String issuerDomain;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		String activeProfile = System.getProperty("spring.profiles.active", "");
		String propertiesFilename = "application" + activeProfile + ".properties";

		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource(propertiesFilename));

		return configurer;
	}

	/*@Bean
	public RestTemplate restTemplate() {

		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(new FormHttpMessageConverter());
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		return restTemplate;
	}*/

	@Bean
	public ImmutableMap<String, String> activeDirectoryConfig() {
		String epFormat = "%s/%s/%s?api-version=1.6";
		ImmutableMap<String, String> config = ImmutableMap.<String, String> builder().put("TENANT", tenant)
				.put("SECRET_KEY", secretKey).put("USER_NAME", userName).put("PASSWORD", password)
				.put("CLIENT_ID", clientId).put("GRAPH_HOST", graphHost)
				.put("USER_EP", String.format(epFormat, graphHost, "myorganization", "users"))
				.put("GROUP_EP", String.format(epFormat, graphHost, "myorganization", "groups")).build();

		return config;
	}

	/*@Bean
	public RestTemplate restTemplate() {
	 return devRestTemplate();
	}*/

	@Bean(name="restTemplate")
//	@Profile({"prod", "stagging"})
	public RestTemplate prodRestTemplate() {

		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(new FormHttpMessageConverter());
		return restTemplate;
	}

    @Bean(name="restTemplate")
//	@Profile("dvlp")
    public RestTemplate restTemplate()
    {

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        String proxyUrl=System.getProperty("https.proxyHost");
        int proxyPort=Integer.valueOf( System.getProperty("https.proxyPort"));
        String proxyUserName = System.getProperty("https.proxyUser");
        String proxyPassword=System.getProperty("https.proxyPassword");

        credsProvider.setCredentials(
                new AuthScope(proxyUrl, proxyPort),
                new UsernamePasswordCredentials(proxyUserName, proxyPassword));

        HttpHost myProxy = new HttpHost(proxyUrl, proxyPort);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setProxy(myProxy).setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

        HttpClient httpClient = clientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
	public String getOAuthUrl() {
		return "https://" + this.authority + this.tenantId + "/oauth2/token";
	}
	
	public String getUserEp(){
		return String.format(epFormat, graphHost, "myorganization", "users");
	}
	
	public String getGroupEp(){
		return  String.format(epFormat, graphHost, "myorganization", "groups");
	}

	public static Logger getLog() {
		return log;
	}

	public String getTenant() {
		return tenant;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getClientId() {
		return clientId;
	}
	

	public String getAuthority() {
		return authority;
	}

	public String getGraphHost() {
		return graphHost;
	}

	public String getResource() {
		return resource;
	}
	
	public String getIssuerDomain(){
		return this.issuerDomain;
	}
	
	public String getIssuer(){
		return String.format("%s/%s/", this.issuerDomain,this.tenantId);
	}

}
