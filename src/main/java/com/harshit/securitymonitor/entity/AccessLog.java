package com.harshit.securitymonitor.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "access_logs")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    // ✅ Explicit default constructor (safe for JPA + Swagger)
    public AccessLog() {}

}
