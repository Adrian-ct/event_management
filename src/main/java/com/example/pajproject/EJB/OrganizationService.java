package com.example.pajproject.EJB;
import com.example.pajproject.DAO.EventDAO;
import com.example.pajproject.DAO.OrganizationDAO;
import com.example.pajproject.model.Organization;
import jakarta.annotation.PostConstruct;
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
    private OrganizationDAO organizationDAO;

    @PostConstruct
    public void init() {
        organizationDAO = new OrganizationDAO(em);
    }

    public List<Organization> getAllOrganizations() {
    return organizationDAO.findAll();
    }

    public Organization createOrganization(Organization organization) {
        return organizationDAO.create(organization);
    }

    public Organization updateOrganization(Organization organization) {
        return organizationDAO.update(organization);
    }

    public boolean deleteOrganization(Long id) {
        try {
            organizationDAO.delete(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting Organization: " + e.getMessage());
            return false;
        }
    }

    public Organization getOrganization(Long id) {
        return organizationDAO.findById(id);
    }
}
