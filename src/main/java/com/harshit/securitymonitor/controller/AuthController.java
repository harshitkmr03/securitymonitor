package com.harshit.securitymonitor.controller;

import com.harshit.securitymonitor.entity.User;
import com.harshit.securitymonitor.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register new user
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
}