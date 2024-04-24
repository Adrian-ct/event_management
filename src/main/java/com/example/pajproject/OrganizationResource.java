package com.example.pajproject;

import com.example.pajproject.EJB.OrganizationService;
import com.example.pajproject.model.Organization;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/organization")
public class OrganizationResource {
    @EJB
    private OrganizationService organizationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrganization(Organization organization) {
        organizationService.createOrganization(organization);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrganization(@PathParam("id") Long id) {
        Organization organization = organizationService.getOrganization(id);
        return Response.ok(organization).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrganizations() {
        List<Organization> organizations = organizationService.getAllOrganizations();
        return Response.ok(organizations).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrganization(@PathParam("id") Long id, Organization organization) {
        organizationService.updateOrganization(organization);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrganization(@PathParam("id") Long id) {
        boolean isDeleted = organizationService.deleteOrganization(id);
        if (isDeleted) {
            return Response.ok("Organization deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Organization not found")
                    .build();
        }
    }
}