package com.example.pajproject.DAO;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface GenericDAO<T> {
    public T findById(Long id);

    public List<T> findAll();

    public T create(T entity);

    public T update(T entity);

    public void delete(Long id);
}
