package com.example.pajproject.EJB;

import com.example.pajproject.model.Attendance;
import com.example.pajproject.model.AttendanceId;
import com.example.pajproject.model.Employee;
import com.example.pajproject.model.Event;
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

    public List<Attendance> getAttendanceByEvent(Long eventId) {
        return em.createQuery("SELECT a FROM Attendance a WHERE a.eventId = :eventId", Attendance.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    public boolean getUserAttendance(Long employeeId, Long eventId) {
        AttendanceId attendanceId = new AttendanceId(employeeId, eventId);
        Attendance attendance = em.find(Attendance.class, attendanceId);

        if (attendance == null) {
            System.err.println("No attendance found");
            return false;
        }

        return true;
    }

    public boolean createAttendance(Attendance attendance) {

        if (getUserAttendance(attendance.getEventId(), attendance.getEmployeeId())) {
            System.err.println("Error creating Attendance: Attendance record already exists");
            return false;
        }

        try {
            em.persist(attendance);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating Attendance: " + e.getMessage());
            return false;
        }
    }

    public Attendance updateAttendance(Attendance attendance) {
        em.merge(attendance);
        return attendance;
    }

    public boolean deleteAttendance(Employee employee, Event event) {
        AttendanceId attendanceId = new AttendanceId(employee.getId(), event.getId());
        Attendance attendance = em.find(Attendance.class, attendanceId);

        if (attendance == null) {
            System.err.println("No attendance found");
            return false;
        }

        System.out.println("Attendance Info:" + attendance.getEmployeeId() + " " + attendance.getEventId());
        try {
            em.remove(attendance);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting Attendance: " + e.getMessage());
            return false;
        }
    }
}
