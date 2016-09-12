package org.nbme.vca.users.security.exception;

/**
 * Invalid Auth Token Exception.
 */
public class InvalidAuthTokenException extends RuntimeException {

    public InvalidAuthTokenException(String msg) {
        super(msg);
    }
}
