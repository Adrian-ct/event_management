package com.example.pajproject.EJB;
import com.example.pajproject.model.Employee;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class EmployeeService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<Employee> getAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    public Employee createEmployee(Employee employee) {
        em.persist(employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee) {
        em.merge(employee);
        return employee;
    }

    public void deleteEmployee(Long employeeId) {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null) {
            em.remove(employee);
        }
    }

    // Additional methods for finding employees by ID, searching by criteria, etc.
}
