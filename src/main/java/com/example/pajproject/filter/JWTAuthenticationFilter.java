package com.example.pajproject.filter;

import com.example.pajproject.controller.AuthController;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.NotAuthorizedException;
import java.io.IOException;
import java.lang.reflect.Method;

@Provider
@RequireJWTAuthentication
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("Filtering request");

        // Get the HTTP Authorization header from the request
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        System.out.println("Authorization header: " + authorizationHeader);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("Invalid authorizationHeader : " + authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        if( AuthController.validateToken(token) ){

            Method method = resourceInfo.getResourceMethod();
            RequireJWTAuthentication requireJWTAuthentication = method.getAnnotation(RequireJWTAuthentication.class);
            String permission = requireJWTAuthentication.Permissions();

            if(permission != null && !permission.isEmpty()){
                if(!AuthController.getRoleClaim(token).equals(permission)){
                    containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("You do not have permission to access this resource").build());
                }
            }
            System.out.println("Token is valid: " + token);
        } else {
            System.out.println("Token is NOT valid: " + token);
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
