package com.harshit.securitymonitor.service;
import com.harshit.securitymonitor.entity.User;
import com.harshit.securitymonitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register new user
    public User register(User user) {
        return userRepository.save(user);
    }

    // Find user by username (used for login)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
