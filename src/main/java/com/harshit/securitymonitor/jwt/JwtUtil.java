package com.harshit.securitymonitor.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Move this to application.properties in future
    private static final String SECRET = "your-super-secret-key-32-characters-long-min!!";

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 1 hour expiration
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // GENERATE TOKEN
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // EXTRACT USERNAME
    public String extractUsername(String token) {

        Claims claims = parseToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    // VALIDATE TOKEN (NEW METHOD)
    public boolean isTokenValid(String token) {

        return parseToken(token) != null;
    }

    // INTERNAL PARSER (IMPROVED)
    private Claims parseToken(String token) {

        try {
            return Jwts.parserBuilder()   // modern (non-deprecated)
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            System.out.println("JWT Error: " + e.getMessage()); // optional logging
            return null;
        }
    }
}