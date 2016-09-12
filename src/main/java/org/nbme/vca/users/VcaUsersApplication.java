package org.nbme.vca.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

//@Configuration
//@SpringBootApplication
////@EnableCaching
//@ComponentScan
////
//@EnableAutoConfiguration

@Configuration
@ComponentScan(basePackages = {"org.nbme.vca.users.config", "org.nbme.vca.users.services", "org.nbme.vca.users.repo", "org.nbme.vca.users.controller", "org.nbme.vca.users.model.builder"})
@EnableAutoConfiguration
@SpringBootApplication
public class VcaUsersApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VcaUsersApplication.class, args);
	}

}
