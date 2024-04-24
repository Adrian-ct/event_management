package com.example.pajproject.EJB;
import com.example.pajproject.model.Organization;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
@LocalBean
public class OrganizationService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<Organization> getAllOrganizations() {
        return em.createQuery("SELECT o FROM Organization o", Organization.class).getResultList();
    }

    public Organization createOrganization(Organization organization) {
        em.persist(organization);
        return organization;
    }

    public Organization updateOrganization(Organization organization) {
        Organization existingOrganization = em.find(Organization.class, organization.getId());
        if (existingOrganization == null) {
            return null;
        }
        return em.merge(organization);
    }

    public boolean deleteOrganization(Long id) {
        Organization organization = em.find(Organization.class, id);
        if (organization == null) {
            return false;
        }
        em.remove(organization);
        return true;
    }

    public Organization getOrganization(Long id) {
        return em.find(Organization.class, id);
    }
}
