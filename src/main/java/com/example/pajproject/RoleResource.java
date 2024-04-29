package com.example.pajproject;

import com.example.pajproject.EJB.RoleService;
import com.example.pajproject.filter.RequireJWTAuthentication;
import com.example.pajproject.model.Role;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/role")
public class RoleResource {
    @EJB
    private RoleService roleService;

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createRole(Role role) {
//        roleService.createRole(role);
//        return Response.status(Response.Status.CREATED).build();
//    }

//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getRole(@PathParam("id") Long id) {
//        Role role = roleService.getRoleById(id);
//        return Response.ok(role).build();
//    }

    @GET
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return Response.ok(roles).build();
    }

//    @PUT
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateRole(@PathParam("id") Long id, Role role) {
//        Role updatedRole = roleService.updateRole(role);
//        if (updatedRole == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Role not found")
//                    .build();
//        }
//        return Response.ok(updatedRole).build();
//    }

//    @DELETE
//    @Path("/{id}")
//    public Response deleteRole(@PathParam("id") Long id) {
//        boolean isDeleted = roleService.deleteRole(id);
//        if (isDeleted) {
//            return Response.ok("Role deleted successfully").build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Role not found")
//                    .build();
//        }
//    }
}