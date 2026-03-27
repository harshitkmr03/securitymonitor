package com.harshit.securitymonitor.controller;

import com.harshit.securitymonitor.entity.AccessLog;
import com.harshit.securitymonitor.repository.AccessLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final AccessLogRepository accessLogRepository;

    public LogController(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    // 🔐 Protected API (JWT required)
    @GetMapping
    public ResponseEntity<?> getLogs() {

        List<AccessLog> logs = accessLogRepository.findAll();

        return ResponseEntity.ok(
                List.of(
                        "Total Logs: " + logs.size(),
                        logs
                )
        );
    }
}