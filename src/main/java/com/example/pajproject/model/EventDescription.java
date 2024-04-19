package com.example.pajproject.model;
import jakarta.persistence.*;

@Entity
@Table(name = "event_description")
public class EventDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    // Getters and Setters omitted for brevity
}

