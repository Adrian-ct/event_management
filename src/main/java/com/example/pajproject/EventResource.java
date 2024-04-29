package com.example.pajproject;

import com.example.pajproject.EJB.EventService;
import com.example.pajproject.EJB.OrganizationService;
import com.example.pajproject.EJB.StatusService;
import com.example.pajproject.filter.RequireJWTAuthentication;
import com.example.pajproject.model.Event;
import com.example.pajproject.model.Organization;
import com.example.pajproject.model.Status;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


//Authorization: Use the @RolesAllowed annotation on your resource methods to restrict access based on user roles.
//You'll need to set up a SecurityContext in your application to hold the authenticated user's information.

@Path("/event")
public class EventResource {
    @EJB
    private EventService eventService;

    @EJB
    private OrganizationService organizationService;

    @EJB
    private StatusService statusService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RequireJWTAuthentication
    public Response createEvent(Event event) {
        Organization organization = organizationService.getOrganization(event.getOrganizerId());
        event.setOrganizer(organization);

        Status status = statusService.getStatus(event.getStatus().getId());
        event.setStatus(status);

        Event createdEvent = eventService.createEvent(event);
        return Response.status(Response.Status.CREATED).entity(createdEvent).build();
    }

    @GET
    @Path("/{id}")
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") Long id) {
        Event event = eventService.getEvent(id);
        return Response.ok(event).build();
    }

    @GET
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return Response.ok(events).build();
    }

    @PUT
    @Path("/{id}")
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEvent(@PathParam("id") Long id, Event event) {
        // Write in the console the id of the event
        System.out.println("Event id: " + id);
        Event existingEvent = eventService.getEvent(id);
        if(existingEvent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Event not found")
                    .build();
        }

        if(event.getStatus() != null) {
            Status status = statusService.getStatus(event.getStatus().getId());
            existingEvent.setStatus(status);
        }

        Event updatedEvent = eventService.updateEvent(existingEvent);
        if (updatedEvent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Event not found")
                    .build();
        }
        return Response.ok(updatedEvent).build();
    }

    @DELETE
    @Path("/{id}")
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEvent(@PathParam("id") Long id) {
        boolean isDeleted = eventService.deleteEvent(id);
        if (isDeleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}