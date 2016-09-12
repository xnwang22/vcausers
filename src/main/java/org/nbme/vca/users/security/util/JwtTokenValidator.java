package org.nbme.vca.users.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.nbme.vca.users.security.dto.JwtUser;
import org.nbme.vca.users.security.service.LoginKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;

/**
 * Jwt Token Validator.
 */
@Component
public class JwtTokenValidator implements TokenValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenValidator.class);

    @Autowired
    LoginKeyService loginKeyService;
    @Override
    public JwtUser parseToken(String token) {
        JwtUser jwtUser = null;
        PublicKey pubKey = null;

        try {
            //  TODO:  This public key value needs to be retrieved from Microsoft on a daily basis to ensure that we have the correct signing key for  signature verification.  See ReadMe.txt for further explanation.
//            String key = "MIIC4jCCAcqgAwIBAgIQQNXrmzhLN4VGlUXDYCRT3zANBgkqhkiG9w0BAQsFADAtMSswKQYDVQQDEyJhY2NvdW50cy5hY2Nlc3Njb250cm9sLndpbmRvd3MubmV0MB4XDTE0MTAyODAwMDAwMFoXDTE2MTAyNzAwMDAwMFowLTErMCkGA1UEAxMiYWNjb3VudHMuYWNjZXNzY29udHJvbC53aW5kb3dzLm5ldDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALyKs/uPhEf7zVizjfcr/ISGFe9+yUOqwpel38zgutvLHmFD39E2hpPdQhcXn4c4dt1fU5KvkbcDdVbP8+e4TvNpJMy/nEB2V92zCQ/hhBjilwhF1ETe1TMmVjALs0KFvbxW9ZN3EdUVvxFvz/gvG29nQhl4QWKj3x8opr89lmq14Z7T0mzOV8kub+cgsOU/1bsKqrIqN1fMKKFhjKaetctdjYTfGzVQ0AJAzzbtg0/Q1wdYNAnhSDafygEv6kNiquk0r0RyasUUevEXs2LY3vSgKsKseI8ZZlQEMtE9/k/iAG7JNcEbVg53YTurNTrPnXJOU88mf3TToX14HpYsS1ECAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAfolx45w0i8CdAUjjeAaYdhG9+NDHxop0UvNOqlGqYJexqPLuvX8iyUaYxNGzZxFgGI3GpKfmQP2JQWQ1E5JtY/n8iNLOKRMwqkuxSCKJxZJq4Sl/m/Yv7TS1P5LNgAj8QLCypxsWrTAmq2HSpkeSk4JBtsYxX6uhbGM/K1sEktKybVTHu22/7TmRqWTmOUy9wQvMjJb2IXdMGLG3hVntN/WWcs5w8vbt1i8Kk6o19W2MjZ95JaECKjBDYRlhG1KmSBtrsKsCBQoBzwH/rXfksTO9JoUYLXiW0IppB7DhNH4PJ5hZI91R8rR0H3/bKkLSuDaKLWSqMhozdhXsIIKvJQ==";
            String key = loginKeyService.getKey();//"MIIC4jCCAcqgAwIBAgIQfQ29fkGSsb1J8n2KueDFtDANBgkqhkiG9w0BAQsFADAtMSswKQYDVQQDEyJhY2NvdW50cy5hY2Nlc3Njb250cm9sLndpbmRvd3MubmV0MB4XDTE2MDQxNzAwMDAwMFoXDTE4MDQxNzAwMDAwMFowLTErMCkGA1UEAxMiYWNjb3VudHMuYWNjZXNzY29udHJvbC53aW5kb3dzLm5ldDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAL23Ba49fdxpus3qOXtv8ueCcePbCIEnL/tiTvp+jcTakGilZzJB3M/ktY9hX6RZ4KLcBjM2SClQmNUivEimTBX0U1N8L06GSE8H91tUKup/ofmm6qciU2qiHH4QNHepBADOTbEACoX78O363tUInJlPS1lVlGAGsi5okV+qN7ZLSauh+fKVM07cfw9A6a58es+bFvrojIqS1264GJjns+4baJCVYA4PMPsgxQsWTaOylbnlJC5MYTY2BpBn57dfLO2VtN+lqE5nWkJluAgoX/6OEyxOVchqWFpuyP/p1feQQb8Jc6JFVSs73in95eVFN3Oj5BsvgQdxPwoahZurD1sCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAe5RxtMLU2i4/vN1YacncR3GkOlbRv82rll9cd5mtVmokAw7kwbFBFNo2vIVkun+n+VdJf+QRzmHGm3ABtKwz3DPr78y0qdVFA3h9P60hd3wqu2k5/Q8s9j1Kq3u9TIEoHlGJqNzjqO7khX6VcJ6BRLzoefBYavqoDSgJ3mkkYCNqTV2ZxDNks3obPg4yUkh5flULH14TqlFIOhXbsd775aPuMT+/tyqcc6xohU5NyYA63KtWG1BLDuF4LEF84oNPcY9i0n6IphEGgz20H7YcLRNjU55pDbWGdjE4X8ANb23kAc75RZn9EY4qYCiqeIAg3qEVKLnLUx0fNKMHmuedjg==";
            String certString = "-----BEGIN CERTIFICATE-----\r\n" + key + "\r\n-----END CERTIFICATE-----";
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            CertificateFactory cf;
            cf = CertificateFactory.getInstance("X.509");
            InputStream stream = new ByteArrayInputStream(certString.getBytes()); //StandardCharsets.UTF_8
            java.security.cert.Certificate cert = cf.generateCertificate(stream);
            pubKey = cert.getPublicKey();

        }
        catch(Exception ex) {
            LOGGER.info("Exception caught creating RSA Public key.", ex);
        }

        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(pubKey)) {
            try {
                Claims body = Jwts.parser()
                        .setSigningKey(pubKey)
                        .parseClaimsJws(token)
                        .getBody();

                jwtUser = new JwtUser();
                jwtUser.setUsername(body.get("name", String.class));
                jwtUser.setId((String) body.get("oid"));
                ArrayList userRoles = null;
                if( body.get("roles") != null) {
                    userRoles = (ArrayList) body.get("roles");
                    jwtUser.setRole((String) userRoles.get(0));
                }

                jwtUser.setAudience(body.getAudience());
                jwtUser.setIssuer(body.getIssuer());
                jwtUser.setClientAppId((String) body.get("appid"));
                jwtUser.setTenantId((String) body.get("tid"));
                LOGGER.info("jwt token parsing done!");

            } catch (JwtException e) {
                // Simply log the exception and null will be returned for the userDto
                LOGGER.error("JWT Exception caught validating JWT Token.", e);
            }
        }

        return jwtUser;
    }
}
