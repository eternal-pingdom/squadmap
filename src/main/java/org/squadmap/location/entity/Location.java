package org.squadmap.location.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "user_id", nullable = false)
    public String userId;

    @Column(nullable = false)
    public Double latitude;

    @Column(nullable = false)
    public Double longitude;

    @Column(nullable = false)
    public LocalDateTime timestamp;
}
