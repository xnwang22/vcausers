package org.nbme.vca.users.sec;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;

/**
 * Date: 11/4/14
 * Time: 10:30 AM
 */
public interface InMemoryUserCreator {

    public void createUsers(InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder);

}
