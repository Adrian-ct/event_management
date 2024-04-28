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

    public Employee getEmployee(Long id) {
        return em.find(Employee.class, id);
    }

    public Employee getEmployeeByEmail(String email) {
        List<Employee> employees = em.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                                     .setParameter("email", email)
                                     .getResultList();
        return employees.isEmpty() ? null : employees.get(0);
    }
}
