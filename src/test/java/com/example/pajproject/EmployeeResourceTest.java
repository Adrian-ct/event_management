package com.example.pajproject;

import com.example.pajproject.EJB.EmployeeService;
import com.example.pajproject.model.Employee;
import com.example.pajproject.model.Organization;
import com.example.pajproject.model.Role;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeResourceTest {

    private EmployeeService employeeService;
    private EmployeeResource employeeResource;

    @BeforeEach
    void setUp() {
        employeeService = mock(EmployeeService.class);
        employeeResource = new EmployeeResource();
        employeeResource.setEmployeeService(employeeService);
    }

    @Test
    void createEmployee() {
        // Given
        Employee newEmployee = new Employee();
        newEmployee.setEmail("test@example.com");
        newEmployee.setPassword("password");

        Role role = new Role();
        role.setId(1L);
        role.setName("admin");

        Organization organization = new Organization();
        organization.setId(1L);

        newEmployee.setRole(role);
        newEmployee.setOrganization(organization);

        when(employeeService.createEmployee(any(Employee.class))).thenReturn(newEmployee);

        // When
        Response response = employeeResource.createEmployee(newEmployee);

        // Then
        verify(employeeService).createEmployee(newEmployee);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    void getEmployee() {
        // Given
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setEmail("test@example.com");
        employee.setPassword("password");

        when(employeeService.getEmployee(1L)).thenReturn(employee);

        // When
        Response response = employeeResource.getEmployee(1L);

        // Then
        verify(employeeService).getEmployee(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getAllEmployees() {
        // Given
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setEmail("test@example.com");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setEmail("test@example.com");

        when(employeeService.getAllEmployees()).thenReturn(java.util.List.of(employee1, employee2));

        // When
        Response response = employeeResource.getAllEmployees();

        // Then
        verify(employeeService).getAllEmployees();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}