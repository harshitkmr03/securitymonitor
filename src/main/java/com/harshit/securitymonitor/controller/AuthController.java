package com.harshit.securitymonitor.controller;

import com.harshit.securitymonitor.dto.LoginRequest;
import com.harshit.securitymonitor.dto.RegisterRequest;
import com.harshit.securitymonitor.entity.User;
import com.harshit.securitymonitor.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // REGISTER USING DTO (FIXED)
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        // Convert DTO → Entity (SAFE WAY)
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        User registeredUser = userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "id", registeredUser.getId(),
                        "username", registeredUser.getUsername(),
                        "message", "User registered successfully"
                ));
    }

    // LOGIN (same as before)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
                                   HttpServletRequest httpServletRequest) {

        String ip = httpServletRequest.getRemoteAddr();

        try {
            String token = userService.login(
                    request.getUsername(),
                    request.getPassword(),
                    ip
            );

            if (token != null) {
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "message", "Login successful"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid username or password"));
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}