package com.example.pajproject.DAO;

import jakarta.persistence.EntityManager;

public abstract class BaseDAO {
    protected EntityManager em;

    public BaseDAO(EntityManager em) {
        this.em = em;
    }
}