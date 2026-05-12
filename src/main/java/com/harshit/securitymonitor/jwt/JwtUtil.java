package com.harshit.securitymonitor.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @jakarta.annotation.PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 1 hour expiration
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // GENERATE TOKEN
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
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
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            return null;
        }
    }
}