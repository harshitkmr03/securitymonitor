package com.harshit.securitymonitor.service;

import com.harshit.securitymonitor.entity.User;
import com.harshit.securitymonitor.entity.AccessLog;
import com.harshit.securitymonitor.entity.ThreatAlert;
import com.harshit.securitymonitor.repository.UserRepository;
import com.harshit.securitymonitor.repository.AccessLogRepository;
import com.harshit.securitymonitor.repository.ThreatAlertRepository;
import com.harshit.securitymonitor.jwt.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccessLogRepository accessLogRepository;
    private final ThreatAlertRepository threatAlertRepository;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final int MAX_FAILED_ATTEMPTS = 5;

    public UserService(UserRepository userRepository,
                       AccessLogRepository accessLogRepository,
                       ThreatAlertRepository threatAlertRepository,
                       JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.accessLogRepository = accessLogRepository;
        this.threatAlertRepository = threatAlertRepository;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER (IMPROVED)
    public User register(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Encrypt password
        user.setPassword(encoder.encode(user.getPassword()));

        // Set default role (IMPORTANT FIX)
        user.setRole("USER");

        // Ensure account is unlocked by default
        user.setIsLocked(false);

        return userRepository.save(user);
    }

    // LOGIN (IMPROVED SECURITY)
    public String login(String username, String password, String ip) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    saveLog(null, ip, "FAILED");
                    return new RuntimeException("Invalid username or password");
                });

        // Check if account locked
        if (Boolean.TRUE.equals(user.getIsLocked())) {
            saveLog(user.getId(), ip, "BLOCKED_ATTEMPT");
            throw new RuntimeException("Account is locked. Please contact admin.");
        }

        // Validate password
        if (!encoder.matches(password, user.getPassword())) {
            handleFailedLogin(user, ip);
            throw new RuntimeException("Invalid username or password");
        }

        // Successful login
        saveLog(user.getId(), ip, "SUCCESS");

        return jwtUtil.generateToken(username);
    }

    // HANDLE FAILED LOGIN
    private void handleFailedLogin(User user, String ip) {

        saveLog(user.getId(), ip, "FAILED");

        Integer userId = user.getId();
        long failedCount = accessLogRepository.countByUserIdAndStatus(userId, "FAILED");

        if (failedCount >= MAX_FAILED_ATTEMPTS) {

            user.setIsLocked(true);
            userRepository.save(user);

            ThreatAlert alert = new ThreatAlert();
            alert.setUserId(userId);
            alert.setReason("Brute force suspicion: " + failedCount + " failed attempts from IP: " + ip);
            alert.setCreatedAt(LocalDateTime.now());

            threatAlertRepository.save(alert);

            saveLog(userId, ip, "ACCOUNT_LOCKED");
        }
    }

    // SAVE ACCESS LOG
    private void saveLog(Integer userId, String ip, String status) {

        AccessLog log = new AccessLog();
        log.setUserId(userId);
        log.setIpAddress(ip);
        log.setStatus(status);
        log.setLoginTime(LocalDateTime.now());

        accessLogRepository.save(log);
    }
}