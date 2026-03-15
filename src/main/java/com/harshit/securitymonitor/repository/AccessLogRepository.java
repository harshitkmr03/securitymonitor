package com.harshit.securitymonitor.repository;

import com.harshit.securitymonitor.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Integer> {

}
