package com.example.pajproject;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

//@Path("/companies")
public class HelloResource {
//    @EJB
//    private CompanyService companyService;
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createCompany(Company company) {
//        companyService.createCompany(company);
//        return Response.status(Response.Status.CREATED).build();
//    }
//
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getCompany(@PathParam("id") Long id) {
//        Company company = companyService.getCompany(id);
//        return Response.ok(company).build();
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllCompanies() {
//        List<Company> companies = companyService.getAllCompanies();
//        return Response.ok(companies).build();
//    }
//
//    @PUT
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateCompany(@PathParam("id") Long id, Company company) {
//        companyService.updateCompany(company);
//        return Response.ok().build();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteCompany(@PathParam("id") Long id) {
//        companyService.deleteCompany(id);
//        return Response.ok().build();
//    }
}