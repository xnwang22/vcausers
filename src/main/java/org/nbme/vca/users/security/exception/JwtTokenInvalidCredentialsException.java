package org.nbme.vca.users.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Jwt Token Invalid Credentials Exception.
 */
public class JwtTokenInvalidCredentialsException extends AuthenticationException {

    public JwtTokenInvalidCredentialsException(String msg) {
        super(msg);
    }
}
