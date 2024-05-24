package com.example.pajproject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Path("/health")
public class HealthCheckResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkHealth() {
        try {
            Query query = entityManager.createNativeQuery("SELECT 1");
            query.getSingleResult();

            return Response.ok("Database is connected").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database is not connected: " + e.getMessage()).build();
        }
    }
}