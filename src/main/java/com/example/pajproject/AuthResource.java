package com.example.pajproject;

import com.example.pajproject.EJB.EmployeeService;
import com.example.pajproject.EJB.OrganizationService;
import com.example.pajproject.EJB.RoleService;
import com.example.pajproject.controller.AuthController;
import com.example.pajproject.model.Employee;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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

        String token = AuthController.createJWT(user.getId(), TimeUnit.DAYS.toMillis(1));

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

        String token = AuthController.createJWT(employee.getId(), TimeUnit.DAYS.toMillis(1));

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
}
