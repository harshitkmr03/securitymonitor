package com.harshit.securitymonitor.service;

import com.harshit.securitymonitor.entity.User;
import com.harshit.securitymonitor.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.harshit.securitymonitor.entity.AccessLog;
import com.harshit.securitymonitor.repository.AccessLogRepository;

import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final AccessLogRepository accessLogRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository,
                       AccessLogRepository accessLogRepository) {
        this.userRepository = userRepository;
        this.accessLogRepository = accessLogRepository;
    }

    // Register new user
    public User register(User user) {

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String login(String username, String password, String ip) {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        AccessLog log = new AccessLog();
        log.setLoginTime(LocalDateTime.now());
        log.setIpAddress(ip);

        if (optionalUser.isEmpty()) {

            log.setStatus("FAILED");
            accessLogRepository.save(log);

            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        log.setUserId(user.getId());

        // 🔥 NEW: Account Lock Check
        if (user.getIsLocked() != null && user.getIsLocked()) {
            return "Account is locked due to multiple failed attempts";
        }

        // Password check
        if (!encoder.matches(password, user.getPassword())) {

            log.setStatus("FAILED");
            accessLogRepository.save(log);

            throw new RuntimeException("Invalid password");
        }

        // Success
        log.setStatus("SUCCESS");
        accessLogRepository.save(log);

        return "Login successful";
    }


}
