package com.example.pajproject.DAO;

import com.example.pajproject.model.Status;
import jakarta.persistence.EntityManager;

import java.util.List;

public class StatusDAO extends BaseDAO implements GenericDAO<Status>{

    public StatusDAO(EntityManager em) {
        super(em);
    }

    @Override
    public Status findById(Long id) {
        return em.find(Status.class, id);
    }

    @Override
    public List<Status> findAll() {
        return em.createQuery("SELECT s FROM Status s", Status.class).getResultList();
    }

    @Override
    public Status create(Status entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Status update(Status entity) {
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        Status status = em.find(Status.class, id);
        if (status != null) {
            em.remove(status);
        }
    }
}
