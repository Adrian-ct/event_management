package com.example.pajproject;

import com.example.pajproject.DTO.EventAttendance;
import com.example.pajproject.EJB.*;
import com.example.pajproject.controller.AuthController;
import com.example.pajproject.filter.RequireJWTAuthentication;
import com.example.pajproject.model.*;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;

@Path("/event")
public class EventResource {
    @EJB
    private EventService eventService;

    @EJB
    private OrganizationService organizationService;

    @EJB
    private StatusService statusService;

    @EJB
    private EmployeeService employeeService;

    @EJB
    private AttendanceService attendanceService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RequireJWTAuthentication(Permissions = "admin")
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
    public Response getAllEvents(@Context HttpHeaders headers) {
        String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer".length()).trim();
        Long userId = AuthController.getIdClaim(token);

        if(userId == null){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token" + token).build();
        }

        Employee employee = employeeService.getEmployee(userId);

        if(employee == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }

        List<Event> events = eventService.getAllEvents();
        List<EventAttendance> eventAttendances = new ArrayList<>();

        for (Event event : events) {
            EventAttendance eventAttendance = new EventAttendance();
            eventAttendance.setEvent(event);
            eventAttendance.setAttending(attendanceService.getUserAttendance(userId, event.getId()));
            eventAttendances.add(eventAttendance);
        }

        return Response.ok(eventAttendances).build();
    }

    @PUT
    @Path("/{id}")
    @RequireJWTAuthentication(Permissions = "admin")
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
    @RequireJWTAuthentication(Permissions = "admin")
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

    @POST
    @Path("/attend/{id}")
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response attendEvent(@PathParam("id") Long id, Employee employee) {
        Event existingEvent = eventService.getEvent(id);
        boolean attendanceStatus = false;

        if(existingEvent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Event not found")
                    .build();
        }

        Employee existingEmployee = employeeService.getEmployee(employee.getId());
        if(existingEmployee == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found")
                    .build();
        }

        if(! attendanceService.createAttendance(new Attendance(existingEmployee.getId(), existingEvent.getId()))){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Attendance record already exists")
                    .build();
        }

        return Response.ok().entity("Attendance created").build();
    }

    @DELETE
    @Path("/attend/{id}")
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelAttendance(@PathParam("id") Long id, @Context HttpHeaders headers) {
        Event existingEvent = eventService.getEvent(id);

        if(existingEvent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Event not found")
                    .build();
        }

        String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer".length()).trim();
        Long userId = AuthController.getIdClaim(token);

        if(userId == null){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token" + token).build();
        }

        Employee existingEmployee = employeeService.getEmployee(userId);

        if (existingEmployee == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found")
                    .build();
        }

        if( !attendanceService.deleteAttendance(existingEmployee, existingEvent)){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error deleting Attendance")
                    .build();
        }
        return Response.ok().entity("Attendance deleted").build();
    }
}