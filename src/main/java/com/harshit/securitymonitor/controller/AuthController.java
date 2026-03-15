package com.harshit.securitymonitor.controller;

import com.harshit.securitymonitor.dto.LoginRequest;
import com.harshit.securitymonitor.entity.User;
import com.harshit.securitymonitor.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request,
                        HttpServletRequest httpServletRequest) {

        String ip = httpServletRequest.getRemoteAddr();

        return userService.login(
                request.getUsername(),
                request.getPassword(),
                ip
        );
    }
}

