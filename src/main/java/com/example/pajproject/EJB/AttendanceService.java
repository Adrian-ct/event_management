package com.example.pajproject.EJB;

import com.example.pajproject.DAO.AttendanceDAO;
import com.example.pajproject.DAO.EventDAO;
import com.example.pajproject.model.Attendance;
import com.example.pajproject.model.AttendanceId;
import com.example.pajproject.model.Employee;
import com.example.pajproject.model.Event;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class AttendanceService {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    private AttendanceDAO attendanceDAO;

    @PostConstruct
    public void init() {
        attendanceDAO = new AttendanceDAO(em);
    }

    public List<Attendance> getAttendanceByEvent(Long eventId) {
        return attendanceDAO.getAttendanceByEvent(eventId);
    }

    public boolean getUserAttendance(Long employeeId, Long eventId) {
        return attendanceDAO.getUserAttendance(employeeId, eventId);
    }

    public boolean createAttendance(Attendance attendance) {
        try {
            attendanceDAO.create(attendance);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating Attendance: " + e.getMessage());
            return false;
        }
    }

    public Attendance updateAttendance(Attendance attendance) {
        return attendanceDAO.update(attendance);
    }

    public boolean deleteAttendance(Employee employee, Event event) {
        try{
            attendanceDAO.delete(employee, event);
            return true;
        }
        catch (Exception e){
            System.err.println("Error deleting Attendance: " + e.getMessage());
            return false;
        }
    }
}
