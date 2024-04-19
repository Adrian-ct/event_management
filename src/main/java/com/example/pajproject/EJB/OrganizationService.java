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
        em.merge(organization);
        return organization;
    }

    public void deleteOrganization(Long orgId) {
        Organization organization = em.find(Organization.class, orgId);
        if (organization != null) {
            em.remove(organization);
        }
    }

    public Organization getOrganization(Long id) {
        return em.find(Organization.class, id);
    }
}
