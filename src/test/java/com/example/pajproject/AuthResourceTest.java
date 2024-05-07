package com.example.pajproject;

import com.example.pajproject.EJB.EmployeeService;
import com.example.pajproject.EJB.OrganizationService;
import com.example.pajproject.EJB.RoleService;
import com.example.pajproject.model.Employee;
import com.example.pajproject.model.Organization;
import com.example.pajproject.model.Role;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthResourceTest {

    @InjectMocks
    private AuthResource authResource;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private RoleService roleService;

    @Mock
    private HttpHeaders httpHeaders;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = mock(EmployeeService.class);
        organizationService = mock(OrganizationService.class);
        roleService = mock(RoleService.class);
        httpHeaders = mock(HttpHeaders.class);
        authResource = new AuthResource();
        authResource.setEmployeeService(employeeService);
        authResource.setOrganizationService(organizationService);
        authResource.setRoleService(roleService);
    }

    @Test
    public void testLogin() {
        Employee mockEmployee = mock(Employee.class);
        Role mockRole = mock(Role.class);
        when(mockRole.getId()).thenReturn(1L);
        mockEmployee.setRole(mockRole);

        when(mockEmployee.getRole()).thenReturn(mockRole);
        when(mockEmployee.getId()).thenReturn(1L);
        when(mockEmployee.getPassword()).thenReturn("password");
        when(employeeService.getEmployeeByEmail(anyString())).thenReturn(mockEmployee);

        Employee user = new Employee();
        user.setEmail("test@example.com");
        user.setPassword("password");

        Response response = authResource.login(user);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testSignup() {
        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getEmail()).thenReturn("test@example.com");
        when(mockEmployee.getPassword()).thenReturn("password");

        Role mockRole = mock(Role.class);
        Organization mockOrganization = mock(Organization.class);


        when(mockRole.getId()).thenReturn(1L);
        when(mockOrganization.getId()).thenReturn(1L);
        when(mockRole.getName()).thenReturn("admin");
        when(mockEmployee.getOrganization()).thenReturn(mockOrganization);
        when(mockEmployee.getRole()).thenReturn(mockRole);
        when(mockEmployee.getId()).thenReturn(1L);

        when(roleService.getRoleById(anyLong())).thenReturn(mockRole);
        when(organizationService.getOrganization(anyLong())).thenReturn(mockOrganization);
        when(employeeService.getEmployeeByEmail(anyString())).thenReturn(null);
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(mockEmployee);

        Response response = authResource.signup(mockEmployee);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
}