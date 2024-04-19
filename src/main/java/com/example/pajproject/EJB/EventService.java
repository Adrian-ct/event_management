package com.example.pajproject.EJB;

import com.example.pajproject.model.Event;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
@LocalBean
public class EventService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<Event> getAllEvents() {
        return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
    }

    public Event createEvent(Event event) {
        em.persist(event);
        return event;
    }

    public Event updateEvent(Event event) {
        em.merge(event);
        return event;
    }

    public void deleteEvent(Long eventId) {
        Event event = em.find(Event.class, eventId);
        if (event != null) {
            em.remove(event);
        }
    }

    // Additional methods for finding events by ID, searching by criteria, etc.
}

