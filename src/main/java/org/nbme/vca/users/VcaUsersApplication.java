package org.nbme.vca.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"org.nbme.vca.users.config", "org.nbme.vca.users.services", "org.nbme.vca.users.repo", "org.nbme.vca.users.controller"})
public class VcaUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(VcaUsersApplication.class, args);
	}

}
