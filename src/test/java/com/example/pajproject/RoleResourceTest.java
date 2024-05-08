package com.example.pajproject;

import com.example.pajproject.EJB.RoleService;
import com.example.pajproject.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleResourceTest {
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = mock(RoleService.class);
    }

    @Test
    void getAllRoles() {
        Role role1 = new Role();
        role1.setId(1L);
        Role role2 = new Role();
        role2.setId(2L);

        List<Role> roles = List.of(role1, role2);

        when(roleService.getAllRoles()).thenReturn(roles);

        assertEquals(roles, roleService.getAllRoles());
    }
}