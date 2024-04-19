package com.example.pajproject.EJB;

import com.example.pajproject.model.EventDescription;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@LocalBean
public class EventDescriptionService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public EventDescription getEventDescription(Long eventId) {
        return em.find(EventDescription.class, eventId);
    }

    public void saveEventDescription(EventDescription description) {
        em.persist(description);
    }

    // Additional methods for updating and deleting descriptions
}
