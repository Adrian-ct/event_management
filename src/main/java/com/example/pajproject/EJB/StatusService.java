package com.example.pajproject.EJB;
import com.example.pajproject.model.Role;
import com.example.pajproject.model.Status;
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

    public List<Status> getAllStatuses() {
        return em.createQuery("SELECT s FROM Status s", Status.class).getResultList();
    }

    public Status getStatus(Long statusId) {
        return em.find(Status.class, statusId);
    }

    public Status createStatus(Status status) {
        em.persist(status);
        return status;
    }

    public Status updateStatus(Status status) {
        em.merge(status);
        return status;
    }

    public boolean deleteStatus(Long statusId) {
        Status status = em.find(Status.class, statusId);
        if (status != null) {
            em.remove(status);
            return true;
        }
        return false;
    }
}
