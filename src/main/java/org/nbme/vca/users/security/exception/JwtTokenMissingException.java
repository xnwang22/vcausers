package org.nbme.vca.users.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Jwt Token Missing Exception.
 */
public class JwtTokenMissingException extends AuthenticationException {


    public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
