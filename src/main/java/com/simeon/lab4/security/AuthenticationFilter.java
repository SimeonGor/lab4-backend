package com.simeon.lab4.security;

import com.simeon.lab4.ejb.expetions.TokenExpiredException;
import com.simeon.lab4.ejb.expetions.TokenIsInvalidException;
import com.simeon.lab4.ejb.services.TokenService;
import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Principal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    @EJB
    private TokenService tokenService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String authorizationHeader = containerRequestContext.getHeaderString(HEADER_NAME);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token invalid").build());
            return;
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();

        try {
            String username = tokenService.validateToken(token);

            containerRequestContext.setSecurityContext(
                    createSecurityContext(username)
            );
        }
        catch (TokenExpiredException e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token expired").build());
        }
        catch (TokenIsInvalidException e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token invalid").build());
        }

    }

    private SecurityContext createSecurityContext(String username) {
        return new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> username;
            }

            @Override
            public boolean isUserInRole(String s) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        };
    }

}
