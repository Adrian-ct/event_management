package com.example.pajproject.DAO;

import com.example.pajproject.model.Employee;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmployeeDAO extends BaseDAO implements GenericDAO<Employee>{
    public EmployeeDAO(EntityManager em) {
        super(em);
    }

    @Override
    public Employee findById(Long id) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    @Override
    public Employee create(Employee entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public Employee update(Employee entity) {
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        Employee employee = em.find(Employee.class, id);
        if (employee != null) {
            em.remove(employee);
        }
    }

    public Employee getEmployeeByEmail(String email) {
        List<Employee> employees = em.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                .setParameter("email", email)
                .getResultList();
        return employees.isEmpty() ? null : employees.get(0);
    }
}
