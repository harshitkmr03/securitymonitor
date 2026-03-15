package com.harshit.securitymonitor.repository;

import com.harshit.securitymonitor.entity.ThreatAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreatAlertRepository extends JpaRepository<ThreatAlert, Integer> {
}
