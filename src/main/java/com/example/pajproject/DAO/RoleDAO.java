package com.example.pajproject.DAO;

import com.example.pajproject.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class RoleDAO extends BaseDAO implements GenericDAO<Role>{
    public RoleDAO(EntityManager em) {
        super(em);
    }

    @Override
    public Role findById(Long id) {
        try {
            return em.find(Role.class, id);
        } catch (NoResultException e) {
            System.err.println("No Role found with ID: " + id);
            return null;
        } catch (Exception e) {
            System.err.println("Error retrieving Role with ID: " + id + " - " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role create(Role entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Role update(Role entity) {
        Role existingRole = em.find(Role.class, entity.getId());
        if (existingRole == null) {
            return null;
        }
        existingRole.setName(entity.getName());
        return existingRole;
    }

    @Override
    public void delete(Long id) {
        Role role = em.find(Role.class, id);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        em.remove(role);
    }
}
