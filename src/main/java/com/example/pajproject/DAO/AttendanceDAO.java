package com.example.pajproject.DAO;

import com.example.pajproject.model.Attendance;
import com.example.pajproject.model.AttendanceId;
import com.example.pajproject.model.Employee;
import com.example.pajproject.model.Event;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AttendanceDAO extends BaseDAO implements  GenericDAO<Attendance>{
    public AttendanceDAO(EntityManager em) {
        super(em);
    }

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

    @Override
    public Attendance findById(Long id) {
        return null;
    }

    @Override
    public List<Attendance> findAll() {
        return List.of();
    }

    @Override
    public Attendance create(Attendance attendance) {
        if (getUserAttendance(attendance.getEventId(), attendance.getEmployeeId())) {
            System.err.println("Error creating Attendance: Attendance record already exists");
            throw new IllegalArgumentException("Attendance record already exists");
        }

        em.persist(attendance);
        return attendance;
    }

    @Override
    public Attendance update(Attendance entity) {
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {

    }

    public void delete(Employee employee, Event event){
        AttendanceId attendanceId = new AttendanceId(employee.getId(), event.getId());
        Attendance attendance = em.find(Attendance.class, attendanceId);

        if (attendance == null) {
            System.err.println("No attendance found");
            throw new IllegalArgumentException("No attendance found");
        }

        em.remove(attendance);
    }
}
