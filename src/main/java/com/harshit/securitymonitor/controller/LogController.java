package com.harshit.securitymonitor.controller;

import com.harshit.securitymonitor.entity.AccessLog;
import com.harshit.securitymonitor.repository.AccessLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final AccessLogRepository accessLogRepository;

    public LogController(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    @GetMapping
    public List<AccessLog> getLogs() {
        return accessLogRepository.findAll();
    }
}
