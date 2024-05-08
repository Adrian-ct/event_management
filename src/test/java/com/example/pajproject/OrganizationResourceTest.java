package com.example.pajproject;

import com.example.pajproject.EJB.OrganizationService;
import com.example.pajproject.model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrganizationResourceTest {
    private OrganizationService organizationService;
    private OrganizationResource organizationResource;
    @BeforeEach
    void setUp() {
        organizationService = mock(OrganizationService.class);

        organizationResource = new OrganizationResource();
        organizationResource.setOrganizationService(organizationService);
    }

    @Test
    void getOrganization() {
        Organization organization = new Organization();
        organization.setId(1L);

        when(organizationService.getOrganization(1L)).thenReturn(organization);

        organizationResource.getOrganization(1L);

        assertEquals(organization, organizationService.getOrganization(1L));
    }

    @Test
    void getAllOrganizations() {
        Organization organization1 = new Organization();
        organization1.setId(1L);
        Organization organization2 = new Organization();
        organization2.setId(2L);

        List<Organization> orgs = List.of(organization1, organization2);

        when(organizationService.getAllOrganizations()).thenReturn(orgs);

        assertEquals(orgs, organizationService.getAllOrganizations());
    }
}