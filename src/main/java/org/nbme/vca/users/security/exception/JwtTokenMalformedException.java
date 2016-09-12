package org.nbme.vca.users.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Jwt Token Malformed Exception.
 */
public class JwtTokenMalformedException extends AuthenticationException {


    public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}
