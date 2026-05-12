package com.harshit.securitymonitor.controller;

import com.harshit.securitymonitor.entity.AccessLog;
import com.harshit.securitymonitor.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Log Management", description = "Endpoints for viewing system access logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    @Operation(summary = "Get all access logs", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getLogs() {
        List<AccessLog> logs = logService.getAllLogs();
        return ResponseEntity.ok(Map.of(
                "total", logs.size(),
                "logs", logs
        ));
    }
}