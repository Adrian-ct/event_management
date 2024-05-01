package com.example.pajproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
@IdClass(AttendanceId.class)
public class Attendance {

    @Id
    private Long eventId;

    @Id
    private Long employeeId;

    public Attendance(Long employeeId, Long eventId) {
        this.employeeId = employeeId;
        this.eventId = eventId;
    }

    public Attendance() {
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
