package com.example.pajproject;

import com.example.pajproject.DTO.EventAttendance;
import com.example.pajproject.EJB.*;
import com.example.pajproject.controller.AuthController;
import com.example.pajproject.model.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventResourceTest {
    HttpHeaders headers = mock(HttpHeaders.class);
    String expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwYWpfcHJvamVjdCIsImlkIjoxMSwicm9sZSI6ImFkbWluIiwiaWF0IjoxNzE1MTU2MjI4LCJleHAiOjE3MTUyNDI2MjgsImp0aSI6ImEyNmUyYzdhLWY2OTEtNGIxMS05YzMzLThmYThkYmM3MTEwMSJ9.iwS6Ubpo0SW5OQu6EP4eErEnA2u-Tx_nOLaSgjW4MRo";

    private EventService eventService;
    private OrganizationService organizationService;
    private EventResource eventResource;
    private StatusService statusService;
    private EmployeeService employeeService;
    private AttendanceService attendanceService;

    @BeforeEach
    void setUp() {
        when(headers.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + expectedToken);

        organizationService = mock(OrganizationService.class);
        eventService = mock(EventService.class);
        statusService = mock(StatusService.class);
        employeeService = mock(EmployeeService.class);
        attendanceService = mock(AttendanceService.class);

        eventResource = new EventResource();
        eventResource.setEventService(eventService);
        eventResource.setOrganizationService(organizationService);
        eventResource.setStatusService(statusService);
        eventResource.setEmployeeService(employeeService);
        eventResource.setAttendanceService(attendanceService);
    }

    @Test
    void createEvent() {
        Event newEvent = new Event();
        newEvent.setName("test");
        newEvent.setDescription("test");

        Organization organization = new Organization();
        organization.setId(1L);

        newEvent.setOrganizer(organization);

        Status status = new Status();
        status.setId(1L);

        newEvent.setStatus(status);

        when(organizationService.getOrganization(any(Long.class))).thenReturn(organization);
        when(eventService.createEvent(any(Event.class))).thenReturn(newEvent);
        when(statusService.getStatus(any(Long.class))).thenReturn(status);

        Response response = eventResource.createEvent(newEvent);

        verify(eventService).createEvent(newEvent);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    void getEvent() {
        Event event = new Event();
        event.setId(1L);

        when(eventService.getEvent(any(Long.class))).thenReturn(event);
        Response response = eventResource.getEvent(1L);

        verify(eventService).getEvent(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getAllEvents() {
        when(headers.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + expectedToken);

        // Given
        Event event1 = new Event();
        event1.setId(1L);

        Event event2 = new Event();
        event2.setId(2L);

        List<Event> events = Arrays.asList(event1, event2);

        Employee employee = new Employee();
        employee.setId(1L);

        when(employeeService.getEmployee(any(Long.class))).thenReturn(employee);
        when(eventService.getAllEvents()).thenReturn(events);
        when(attendanceService.getUserAttendance(any(Long.class), any(Long.class))).thenReturn(true);

        // When
        Response response = eventResource.getAllEvents(headers);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void updateEvent() {
        Event event = new Event();
        event.setId(1L);

        Status status = new Status();
        status.setId(1L);
        event.setStatus(status);

        when(statusService.getStatus(any(Long.class))).thenReturn(status);
        when(eventService.getEvent(any(Long.class))).thenReturn(event);
        when(eventService.updateEvent(any(Event.class))).thenReturn(event);

        Response response = eventResource.updateEvent(1L, event);

        verify(eventService).updateEvent(event);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void deleteEvent() {
        Event event = new Event();
        event.setId(1L);

        when(eventService.getEvent(any(Long.class))).thenReturn(event);
        when(eventService.deleteEvent(any(Long.class))).thenReturn(true);

        Response response = eventResource.deleteEvent(1L);
        verify(eventService).deleteEvent(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void attendEvent() {
        Event event = new Event();
        event.setId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        when(eventService.getEvent(any(Long.class))).thenReturn(event);
        when(employeeService.getEmployee(any(Long.class))).thenReturn(employee);
        when(attendanceService.createAttendance(any(Attendance.class))).thenReturn(true);

        Response response = eventResource.attendEvent(1L, employee);
        verify(attendanceService).createAttendance(any(Attendance.class));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void cancelAttendance() {
        Event event = new Event();
        event.setId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        when(eventService.getEvent(any(Long.class))).thenReturn(event);
        when(employeeService.getEmployee(any(Long.class))).thenReturn(employee);
        when(attendanceService.deleteAttendance(any(Employee.class), any(Event.class))).thenReturn(true);
        Response response = eventResource.cancelAttendance(1L, headers);
        verify(attendanceService).deleteAttendance(any(Employee.class), any(Event.class));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}