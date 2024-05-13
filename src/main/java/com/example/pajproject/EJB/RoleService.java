package com.example.pajproject.EJB;

import com.example.pajproject.DAO.OrganizationDAO;
import com.example.pajproject.DAO.RoleDAO;
import com.example.pajproject.model.Role;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class RoleService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private RoleDAO roleDAO;

    @PostConstruct
    public void init() {
        roleDAO = new RoleDAO(em);
    }

    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    public Role getRoleById(Long id) {
        return roleDAO.findById(id);
    }

    public Role createRole(Role role) {
        return roleDAO.create(role);
    }

    public Role updateRole(Role role) {
        return roleDAO.update(role);
    }

    public boolean deleteRole(Long roleId) {
        try {
            roleDAO.delete(roleId);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting Role: " + e.getMessage());
            return false;
        }
    }
}