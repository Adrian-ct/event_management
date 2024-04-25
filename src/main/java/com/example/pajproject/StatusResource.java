package com.example.pajproject;

import com.example.pajproject.EJB.StatusService;
import com.example.pajproject.model.Status;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/status")
public class StatusResource {
    @EJB
    private StatusService statusService;

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createStatus(Status status) {
//        statusService.createStatus(status);
//        return Response.status(Response.Status.CREATED).build();
//    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus(@PathParam("id") Long id) {
        Status status = statusService.getStatus(id);
        return Response.ok(status).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStatuses() {
        List<Status> statuses = statusService.getAllStatuses();
        return Response.ok(statuses).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("id") Long id, Status status) {
        Status updatedStatus = statusService.updateStatus(status);
        if (updatedStatus == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Status not found")
                    .build();
        }
        return Response.ok(updatedStatus).build();
    }

//    @DELETE
//    @Path("/{id}")
//    public Response deleteStatus(@PathParam("id") Long id) {
//        boolean isDeleted = statusService.deleteStatus(id);
//        if (isDeleted) {
//            return Response.ok("Status deleted successfully").build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Status not found")
//                    .build();
//        }
//    }
}