package org.covid19.contactbase.auth;

import org.covid19.contactbase.controller.RequiresAdminAuthentication;
import org.covid19.contactbase.controller.RequiresAuthorityAuthentication;
import org.covid19.contactbase.controller.RequiresDeviceAuthentication;
import org.covid19.contactbase.model.Authority;
import org.covid19.contactbase.model.Device;
import org.covid19.contactbase.util.Jwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${admin.key}")
    private String adminKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {

            Object handlerBean = ((HandlerMethod) handler).getBean();

            if (handlerBean instanceof RequiresDeviceAuthentication) {
                String token = extractToken(request);

                Device device = Jwt.getDevice(token, jwtSecretKey);

                if (device == null) {
                    throw new RuntimeException("Invalid access token");
                }

                ThreadLocalWrapper.setDevice(device);
            } else if (handlerBean instanceof RequiresAdminAuthentication) {
                String token = extractToken(request);

                if (!adminKey.equals(token)) {
                    throw new RuntimeException("Invalid access token");
                }
            } else if (handlerBean instanceof RequiresAuthorityAuthentication) {
                String token = extractToken(request);

                Authority authority = Jwt.getAuthority(token, jwtSecretKey);

                if (authority == null) {
                    throw new RuntimeException("Invalid access token");
                }

                ThreadLocalWrapper.setAuthority(authority);
            }
        }

        return true;
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            throw new RuntimeException("Invalid access token");
        }

        String[] authorizationComponents = authorizationHeader.split("\\s+");

        if (authorizationComponents.length != 2) {
            throw new RuntimeException("Invalid access token");
        }

        if (!authorizationComponents[0].equals("Bearer")) {
            throw new RuntimeException("Invalid access token");
        }

        return authorizationComponents[1];
    }
}