package com.harshit.securitymonitor.repository;

import com.harshit.securitymonitor.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Integer> {
    // Parameter matches the Integer userId field in your AccessLog entity
    long countByUserIdAndStatus(Integer userId, String status);
    List<AccessLog> findByUserId(Integer userId);
}