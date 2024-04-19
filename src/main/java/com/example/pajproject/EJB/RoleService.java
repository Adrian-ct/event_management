package com.example.pajproject.EJB;

import com.example.pajproject.model.Role;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class RoleService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<Role> getAllRoles() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    public Role getRoleById(Long roleId) {
        return em.find(Role.class, roleId);
    }

    // Additional methods for creating, updating, and deleting roles (if needed)
}
