package com.example.pajproject.EJB;

import com.example.pajproject.model.Event;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
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
        Date now = new Date();
        event.setCreatedAt(now);
        event.setChangedAt(now);
        em.persist(event);
        return event;
    }

    public Event getEvent(Long eventId) {
        return em.find(Event.class, eventId);
    }

    public Event updateEvent(Event event) {
        event.setChangedAt(new Date());
        em.merge(event);
        return event;
    }

    public boolean deleteEvent(Long eventId) {
        Event event = em.find(Event.class, eventId);
        if (event != null) {
            em.remove(event);
            return true;
        }
        return false;
    }
}

