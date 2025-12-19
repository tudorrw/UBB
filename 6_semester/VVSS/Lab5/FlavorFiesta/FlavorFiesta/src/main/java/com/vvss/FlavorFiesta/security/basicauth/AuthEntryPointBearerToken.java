package com.vvss.FlavorFiesta.security.basicauth;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Component
public class AuthEntryPointBearerToken implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        Throwable cause = authException.getCause();
        if (cause instanceof ResponseStatusException) {
            int statusCode = ((ResponseStatusException) cause).getStatusCode().value();
            log.error("Error: {} {}", statusCode, authException.getMessage());
            response.sendError(statusCode, "Error: Unauthorized");
        } else {
            log.error("Unauthorized error: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
        }
    }
}