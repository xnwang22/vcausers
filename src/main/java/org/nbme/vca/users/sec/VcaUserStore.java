package org.nbme.vca.users.sec;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Date: 11/4/14
 * Time: 2:14 PM
 */
public interface VcaUserStore {
    List<String> getRoles(String username) throws UsernameNotFoundException;
}
