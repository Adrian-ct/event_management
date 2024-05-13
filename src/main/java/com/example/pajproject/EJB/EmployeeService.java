package com.example.pajproject.EJB;
import com.example.pajproject.DAO.EmployeeDAO;
import com.example.pajproject.DAO.EventDAO;
import com.example.pajproject.model.Employee;
import jakarta.annotation.PostConstruct;
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
    private EmployeeDAO employeeDAO;

    @PostConstruct
    public void init() {
        employeeDAO = new EmployeeDAO(em);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return employeeDAO.create(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeDAO.update(employee);
    }

    public void deleteEmployee(Long employeeId) {
        employeeDAO.delete(employeeId);
    }

    public Employee getEmployee(Long id) {
        return em.find(Employee.class, id);
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeDAO.getEmployeeByEmail(email);
    }
}
