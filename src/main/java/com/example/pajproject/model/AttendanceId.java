package com.example.pajproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

public class AttendanceId implements Serializable {
    private Long employeeId;
    private Long eventId;

    public AttendanceId() {
    }

    public AttendanceId(Long employeeId, Long eventId) {
        this.employeeId = employeeId;
        this.eventId = eventId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}