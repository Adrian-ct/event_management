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

    public Role createRole(Role role) {
        em.persist(role);
        return role;
    }

    public Role updateRole(Role role) {
        Role existingRole = em.find(Role.class, role.getId());
        if (existingRole == null) {
            return null;
        }
        existingRole.setName(role.getName());
        return existingRole;
    }

    public boolean deleteRole(Long roleId) {
        Role role = em.find(Role.class, roleId);
        if (role == null) {
            return false;
        }
        em.remove(role);
        return true;
    }
}