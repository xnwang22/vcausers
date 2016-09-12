package org.nbme.vca.users.sec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 11/4/14
 * Time: 2:10 PM
 */
public class VcaUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T> {
    private static final Logger logger = LoggerFactory.getLogger(VcaUserDetailsService.class);

    protected VcaUserStore vcaUserStore;

    public VcaUserDetailsService(VcaUserStore vcaUserStore) {
        this.vcaUserStore = vcaUserStore;
    }

    @Override
    public UserDetails loadUserDetails(T token) throws UsernameNotFoundException {
        logger.debug("Looking up user " + token.getName());
        List<String> roles = vcaUserStore.getRoles(token.getName());
        logger.debug("Found user " + token.getName());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new User(token.getName(), "secret", authorities);
    }

}
