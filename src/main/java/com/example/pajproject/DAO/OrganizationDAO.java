package com.example.pajproject.DAO;

import com.example.pajproject.model.Organization;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OrganizationDAO extends BaseDAO implements GenericDAO<Organization>{
    public OrganizationDAO(EntityManager em) {
        super(em);
    }

    @Override
    public Organization findById(Long id) {
        return em.find(Organization.class, id);
    }

    @Override
    public List<Organization> findAll() {
        return em.createQuery("SELECT o FROM Organization o", Organization.class).getResultList();
    }

    @Override
    public Organization create(Organization entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Organization update(Organization entity) {
        Organization existingOrganization = em.find(Organization.class, entity.getId());
        if (existingOrganization == null) {
            return null;
        }
        return em.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Organization organization = em.find(Organization.class, id);
        if (organization == null) {
            throw new IllegalArgumentException("Organization not found");
        }
        em.remove(organization);
    }
}
