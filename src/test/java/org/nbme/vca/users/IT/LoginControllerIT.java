package org.nbme.vca.users.IT;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nbme.vca.users.VcaUsersApplication;
import org.nbme.vca.users.config.AdConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AdConfig.class})
//@WebAppConfiguration
@ComponentScan("org.nbme.vca.users.controller")
@SpringApplicationConfiguration(classes = VcaUsersApplication.class)
@WebIntegrationTest
public class LoginControllerIT {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testLogin() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Basic admin:admin");//""Basic User4:Test1234");

        try {
            restTemplate.exchange("http://localhost:8888/validate/login", HttpMethod.GET, new HttpEntity<>("", httpHeaders), String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }
    }

}