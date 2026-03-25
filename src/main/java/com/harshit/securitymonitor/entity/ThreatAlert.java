package com.harshit.securitymonitor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "threat_alerts")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ThreatAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ✅ Default constructor (important for JPA + Swagger)
    public ThreatAlert() {}


}
