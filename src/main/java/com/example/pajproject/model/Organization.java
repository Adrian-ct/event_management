package com.example.pajproject.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Getters and Setters omitted for brevity
}
