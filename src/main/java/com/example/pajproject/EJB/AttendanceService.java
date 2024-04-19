package com.example.pajproject.EJB;

import com.example.pajproject.model.Attendance;
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
        return em.createQuery("SELECT a FROM Attendance a WHERE a.event.id = :eventId", Attendance.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    public Attendance createAttendance(Attendance attendance) {
        em.persist(attendance);
        return attendance;
    }

    public Attendance updateAttendance(Attendance attendance) {
        em.merge(attendance);
        return attendance;
    }

    public void deleteAttendance(Long attendanceId) {
        Attendance attendance = em.find(Attendance.class, attendanceId);
        if (attendance != null) {
            em.remove(attendance);
        }
    }

    // Additional methods for managing attendance records
}
