package org.nbme.vca.users.config;

import org.nbme.vca.users.VcaUsersApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServletInitializer extends org.springframework.boot.context.web.SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VcaUsersApplication.class);
	}

}
