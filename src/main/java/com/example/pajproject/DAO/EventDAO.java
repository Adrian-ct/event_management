package com.example.pajproject.DAO;

import com.example.pajproject.model.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

public class EventDAO extends BaseDAO implements GenericDAO<Event>{
    public EventDAO(EntityManager em) {
        super(em);
    }

    @Override
    public Event findById(Long id) {
        return em.find(Event.class, id);
    }

    @Override
    public List<Event> findAll() {
        return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
    }

    @Override
    public Event create(Event event) {
        Date now = new Date();
        event.setCreatedAt(now);
        event.setChangedAt(now);
        em.persist(event);
        return event;
    }

    @Override
    public Event update(Event event) {
        event.setChangedAt(new Date());
        em.merge(event);
        return event;
    }

    @Override
    public void delete(Long id) {
        Event event = em.find(Event.class, id);
        if (event != null) {
            em.remove(event);
        }
    }
}
