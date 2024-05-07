package com.example.pajproject;

import com.example.pajproject.EJB.EmployeeService;
import com.example.pajproject.EJB.OrganizationService;
import com.example.pajproject.EJB.RoleService;
import com.example.pajproject.controller.AuthController;
import com.example.pajproject.model.Employee;
import com.example.pajproject.model.Role;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.TimeUnit;

@Path("/auth")
public class AuthResource {

    @EJB
    private EmployeeService employeeService;

    @EJB
    private OrganizationService organizationService;

    @EJB
    private RoleService roleService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(Employee user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        Employee employee = employeeService.getEmployeeByEmail(user.getEmail());

        if (employee == null || !employee.getPassword().equals(user.getPassword())){
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .build();
        }

        String token = AuthController.createJWT(employee.getId(),employee.getRole().getName(), TimeUnit.DAYS.toMillis(1));

        return Response
                .status(Response.Status.OK)
                .entity(token)
                .build();
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response signup(Employee user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        Employee employee = employeeService.getEmployeeByEmail(user.getEmail());

        if (employee != null){
            return Response
                    .status(Response.Status.CONFLICT)
                    .build();
        }

        try{
            user.setRole(roleService.getRoleById(user.getRole().getId()));
            user.setOrganization(organizationService.getOrganization(user.getOrganization().getId()));
            employee = employeeService.createEmployee(user);
        } catch (Exception e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        String token = AuthController.createJWT(employee.getId(), employee.getRole().getName(), TimeUnit.DAYS.toMillis(1));

        if(token == null){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(token)
                .build();
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@Context HttpHeaders headers) {
        String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("No header").build();
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        Long userId = AuthController.getIdClaim(token);
        if(userId == null){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token" + token).build();
        }

        Employee employee = employeeService.getEmployee(userId);
        if(employee == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(employee).build();
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
