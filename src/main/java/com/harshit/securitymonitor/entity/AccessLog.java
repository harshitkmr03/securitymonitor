package com.harshit.securitymonitor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
@Data
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "status")
    private String status;

}
