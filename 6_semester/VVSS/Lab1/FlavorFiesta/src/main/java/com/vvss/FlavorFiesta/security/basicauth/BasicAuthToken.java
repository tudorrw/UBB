package com.vvss.FlavorFiesta.security.basicauth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Base64;

@AllArgsConstructor
@Getter
public class BasicAuthToken {
    private String username;

    private String password;

    public static BasicAuthToken fromString(String token) {
        String[] split = token.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid Basic auth token: " + token);
        }
        return new BasicAuthToken(split[0], split[1]);
    }

    public static BasicAuthToken fromAuthorizationHeader(String headerAuth) {
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Basic ")) {
            String base64EncodedString = headerAuth.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
            String decodedString = new String(decodedBytes);
            return BasicAuthToken.fromString(decodedString);
        }
        return null;
    }

    @Override
    public String toString() {
        return username + ":" + password;
    }

    public String toAuthorizationHeader() {
        String base64EncodedString = Base64.getEncoder().encodeToString(toString().getBytes());
        return "Basic " + base64EncodedString;
    }
}
