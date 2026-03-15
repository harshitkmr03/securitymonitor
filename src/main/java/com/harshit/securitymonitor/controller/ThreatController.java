package com.harshit.securitymonitor.controller;

import com.harshit.securitymonitor.entity.ThreatAlert;
import com.harshit.securitymonitor.repository.ThreatAlertRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/threats")
public class ThreatController {

    private final ThreatAlertRepository threatAlertRepository;

    public ThreatController(ThreatAlertRepository threatAlertRepository) {
        this.threatAlertRepository = threatAlertRepository;
    }

    @GetMapping
    public List<ThreatAlert> getThreats() {
        return threatAlertRepository.findAll();
    }
}
