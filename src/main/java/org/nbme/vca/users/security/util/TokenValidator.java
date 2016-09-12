package org.nbme.vca.users.security.util;

import org.nbme.vca.users.security.dto.JwtUser;

/**
 * Token Validator interface.
 */
public interface TokenValidator {
    JwtUser parseToken(String token);
}
