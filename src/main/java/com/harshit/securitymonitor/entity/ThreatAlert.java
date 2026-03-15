package com.harshit.securitymonitor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "threat_alerts")
@Data
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

}
