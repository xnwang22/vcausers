package org.nbme.vca.users.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.nbme.vca.users.config.AdConfig;
import org.nbme.vca.users.model.VcaUser;
import org.nbme.vca.users.repo.VcaUserRepo;
import org.nbme.vca.users.security.dto.JwtUser;
import org.nbme.vca.users.security.exception.JwtTokenInvalidCredentialsException;
import org.nbme.vca.users.security.exception.JwtTokenMalformedException;
import org.nbme.vca.users.security.model.AuthenticatedUser;
import org.nbme.vca.users.security.model.JwtAuthenticationToken;
import org.nbme.vca.users.security.util.JwtTokenValidator;
import org.nbme.vca.users.utils.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Jwt Authentication Provider.
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtTokenValidator jwtTokenValidator;
    
    @Autowired
    private AdConfig adConfig;
    @Autowired
    private VcaUserRepo vcaUserRepo;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
       
    	JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();

        JwtUser parsedUser = jwtTokenValidator.parseToken(token);

        if (parsedUser == null) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        }


         VcaUser vcaUserFromRepo = vcaUserRepo.findByUName(parsedUser.getUsername());
        if(vcaUserFromRepo == null) {
            throw new JwtTokenMalformedException("User not in repo");
        }

        if (StringUtils.isEmpty(parsedUser.getTenantId()) || !parsedUser.getTenantId().equals(adConfig.getTenantId())) {
            throw new JwtTokenInvalidCredentialsException("JWT token tenant id is not valid");
        }

        //  Validate Client Application Id
        if (StringUtils.isEmpty(parsedUser.getClientAppId()) || !parsedUser.getClientAppId().equals(adConfig.getClientId())) {
            throw new JwtTokenInvalidCredentialsException("JWT token client application id is not valid");
        }

        //  Validate Issuer
        String issuer = adConfig.getIssuer();
        if (StringUtils.isEmpty(parsedUser.getIssuer()) || !parsedUser.getIssuer().equals(issuer)) {
            throw new JwtTokenInvalidCredentialsException("JWT token issuer is not valid");
        }
//
//        //  Validate Audience
//        String audience = tenantUri + resouceId;
//        if (StringUtils.isEmpty(parsedUser.getIssuer()) || !parsedUser.getIssuer().equals(issuer)) {
//            throw new JwtTokenInvalidCredentialsException("JWT token audience is not valid");
//        }

//        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

//      return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token, authorityList);
//        SimpleGrantedAuthority role =
//                vcaUserFromRepo.getRoleStr().equalsIgnoreCase("admin") ? new SimpleGrantedAuthority("ROLE_ADMIN"):new SimpleGrantedAuthority("ROLE_USER");
        
          List<String> roles = vcaUserFromRepo.getRoles();
          Collection<SimpleGrantedAuthority> grants = new ArrayList<SimpleGrantedAuthority>();
          for(String role : roles){
        	  if(UserConstants.ROLE_ADMIN.equalsIgnoreCase(role)){
        		  grants.add(new SimpleGrantedAuthority(UserConstants.ROLE_ADMIN));
        	  }
        	  else if(UserConstants.ROLE_PARTICIPANT.equalsIgnoreCase(role)){
        		  grants.add(new SimpleGrantedAuthority(UserConstants.ROLE_PARTICIPANT));
        	  }
          }
                
          return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token,grants);
    }


}
