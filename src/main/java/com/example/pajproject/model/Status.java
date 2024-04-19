package com.example.pajproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    // Getters and Setters omitted for brevity
}
