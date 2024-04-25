package com.example.pajproject;

import com.example.pajproject.EJB.EventService;
import com.example.pajproject.EJB.OrganizationService;
import com.example.pajproject.model.Event;
import com.example.pajproject.model.Organization;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/event")
public class EventResource {
    @EJB
    private EventService eventService;

    @EJB
    private OrganizationService organizationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEvent(Event event) {
        Organization organization = organizationService.getOrganization(event.getOrganizerId());
        event.setOrganizer(organization);
        Event createdEvent = eventService.createEvent(event);
        return Response.status(Response.Status.CREATED).entity(createdEvent).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") Long id) {
        Event event = eventService.getEvent(id);
        return Response.ok(event).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return Response.ok(events).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEvent(@PathParam("id") Long id, Event event) {
        Event updatedEvent = eventService.updateEvent(event);
        if (updatedEvent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Event not found")
                    .build();
        }
        return Response.ok(updatedEvent).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEvent(@PathParam("id") Long id) {
        boolean isDeleted = eventService.deleteEvent(id);
        if (isDeleted) {
            return Response.ok("Event deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Event not found")
                    .build();
        }
    }
}