package org.nbme.vca.users.config;

import java.util.Arrays;

import org.nbme.vca.users.security.JwtAuthenticationEntryPoint;
import org.nbme.vca.users.security.filter.JwtAuthenticationFilter;
import org.nbme.vca.users.utils.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Security Configuration.
 */
@Configuration
@ComponentScan(basePackages = {"org.nbme.vca.users.security"})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    @Qualifier(value = "jwtAuthenticationProvider")
    private AuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    @Qualifier(value = "jwtAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("user").roles(UserConstants.ROLE_PARTICIPANT)
                .and()
                .withUser("admin").password("admin").roles(UserConstants.ROLE_ADMIN, UserConstants.ROLE_PARTICIPANT);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/", "/resources/**", "/validate/login", "/user/**", "/userRole/**"); //
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
//        http
//                
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                //  Error Handler for failed authentication and authorization requests
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                //  Custom JWT Token based security filter
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                //  CSRF protection is not needed
//                .csrf().disable()
//                //  All requests must be submitted using a secured channel (Https)
////          .requiresChannel().anyRequest().requiresSecure()
////          .and()
//                //  All remaining endpoints are secured
//                .authorizeRequests().anyRequest().authenticated();
//    	http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
    	
    	
    	this.decorateAllowEverything(http);
    	
    	
//    	http
//    	//Do not create a Http Session
//    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//    	//allow validate login calls. 
//    	.authorizeRequests().antMatchers("/validate/login").permitAll().and()
//    	//add the auth filter 
//    	.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//    	//CSRF protection is not needed
//    	.csrf().disable()
//    	//All requests must be submitted using a secured channel (Https)
////    	.requiresChannel().anyRequest().requiresSecure().and()
////    	//All remaining endpoints are secured
////    	.authorizeRequests().anyRequest().authenticated();
    	;
    }
    
    private void decorateAllowEverything(HttpSecurity http) throws Exception {
      http
    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    	//CSRF protection is not needed
    	.csrf().disable()
    	//allow validate login calls. 
    	.authorizeRequests().antMatchers("/**").permitAll();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(jwtAuthenticationProvider));
    }

//    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
        return authenticationFilter;
    }
}
