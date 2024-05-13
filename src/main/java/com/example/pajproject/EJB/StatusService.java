package com.example.pajproject.EJB;
import com.example.pajproject.DAO.RoleDAO;
import com.example.pajproject.DAO.StatusDAO;
import com.example.pajproject.model.Role;
import com.example.pajproject.model.Status;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class StatusService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private StatusDAO statusDAO;

    @PostConstruct
    public void init() {
        statusDAO = new StatusDAO(em);
    }

    public List<Status> getAllStatuses() {
        return statusDAO.findAll();
    }

    public Status getStatus(Long statusId) {
        return statusDAO.findById(statusId);
    }

    public Status createStatus(Status status) {
        return statusDAO.create(status);
    }

    public Status updateStatus(Status status) {
        return statusDAO.update(status);
    }

    public boolean deleteStatus(Long statusId) {
        try {
            statusDAO.delete(statusId);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting Status: " + e.getMessage());
            return false;
        }
    }
}
