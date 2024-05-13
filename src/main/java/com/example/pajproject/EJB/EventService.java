package com.example.pajproject.EJB;

import com.example.pajproject.DAO.EventDAO;
import com.example.pajproject.model.Event;
import jakarta.annotation.PostConstruct;
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

    private EventDAO eventDAO;

    @PostConstruct
    public void init() {
        eventDAO = new EventDAO(em);
    }

    public List<Event> getAllEvents() {
        return eventDAO.findAll();
    }

    public Event createEvent(Event event) {
        return eventDAO.create(event);
    }

    public Event getEvent(Long eventId) {
        return eventDAO.findById(eventId);
    }

    public Event updateEvent(Event event) {
        return eventDAO.update(event);
    }

    public boolean deleteEvent(Long eventId) {
        try {
            eventDAO.delete(eventId);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting Event: " + e.getMessage());
            return false;
        }
    }
}

