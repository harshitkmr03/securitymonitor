package com.harshit.securitymonitor.service;

import com.harshit.securitymonitor.entity.AccessLog;
import com.harshit.securitymonitor.repository.AccessLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final AccessLogRepository accessLogRepository;

    public LogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public List<AccessLog> getAllLogs() {
        return accessLogRepository.findAll();
    }
    
    // Add filtering logic here in the future
}
