package com.example.pajproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_organization")
public class EmployeeOrganization {

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
}
//
