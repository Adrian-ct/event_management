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

        if(employee == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(employee.getId() != null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(employee.getEmail() == null || employee.getEmail().isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(employee.getPassword() == null || employee.getPassword().isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(employee.getRole() == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(employee.getOrganization() == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(employee.getOrganization().getId() == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(employee.getRole().getId() == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        employeeService.createEmployee(employee);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") Long id) {
        if(id == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        Employee employee = employeeService.getEmployee(id);

        if(employee == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(employee).build();
    }

    @GET
    @RequireJWTAuthentication
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return Response.ok(employees).build();
    }

//    @PUT
//    @Path("/{id}")
//    @RequireJWTAuthentication
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateEmployee(@PathParam("id") Long id, Employee employee) {
//        employeeService.updateEmployee(employee);
//        return Response.ok().build();
//    }

//    @DELETE
//    @Path("/{id}")
//    @RequireJWTAuthentication
//    public Response deleteEmployee(@PathParam("id") Long id) {
//        employeeService.deleteEmployee(id);
//        return Response.ok().build();
//    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}