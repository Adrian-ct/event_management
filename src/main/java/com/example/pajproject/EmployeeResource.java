package com.example.pajproject;

import com.example.pajproject.EJB.EmployeeService;
import com.example.pajproject.filter.RequireJWTAuthentication;
import com.example.pajproject.model.Employee;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/employee")
public class EmployeeResource {
    @EJB
    private EmployeeService employeeService;

    @POST
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee employee) {
        employeeService.createEmployee(employee);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") Long id) {
        Employee employee = employeeService.getEmployee(id);
        return Response.ok(employee).build();
    }

    @GET
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return Response.ok(employees).build();
    }

    @PUT
    @Path("/{id}")
    @RequireJWTAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") Long id, Employee employee) {
        employeeService.updateEmployee(employee);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RequireJWTAuthentication
    public Response deleteEmployee(@PathParam("id") Long id) {
        employeeService.deleteEmployee(id);
        return Response.ok().build();
    }
}