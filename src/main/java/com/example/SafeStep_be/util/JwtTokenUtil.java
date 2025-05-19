package com.example.SafeStep_be.util;

import com.example.SafeStep_be.bo.RefreshTokenBo;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
@Getter
public class JwtTokenUtil {

    private final SecretKey secretKey;
    private final SecretKey refreshSecretKey;
    private final long jwtExpirationMs;
    private final long jwtRefreshExpirationMs;

    public JwtTokenUtil(
            @Value("${jwt.secret_key}") String jwtSecret,
            @Value("${jwt.refresh_secret_key}") String jwtRefreshSecret,
            @Value("${jwt.expiration_time}") long jwtExpirationMs,
            @Value("${jwt.refreshExpiration}") long jwtRefreshExpirationMs) {

        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        byte[] refreshKeyBytes = Base64.getDecoder().decode(jwtRefreshSecret);
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshKeyBytes);

        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject, secretKey);
    }

    public String extractUsernameFromRefreshToken(String token) {
        try {
            return extractClaim(token, Claims::getSubject, refreshSecretKey);
        } catch (Exception e) {
            System.out.println("Error extracting username from refresh token: " + e.getMessage());
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, SecretKey key) {
        try {
            Claims claims = extractClaims(token, key);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            System.out.println("Error extracting claim: " + e.getMessage());
            return null;
        }
    }

    public boolean isTokenExpired(String token, SecretKey key) {
        try {
            Date expirationDate = extractExpiration(token, key);
            boolean isExpired = expirationDate.before(new Date());
            System.out.println("Token expiration date: " + expirationDate);
            System.out.println("Current date: " + new Date());
            System.out.println("Token expired: " + isExpired);
            return isExpired;
        } catch (Exception e) {
            System.out.println("Error checking token expiration: " + e.getMessage());
            return true;
        }
    }

    public boolean isTokenValid(String token, String email) {
        try {
            String extractedEmail = extractUsername(token);
            boolean emailsMatch = extractedEmail.equals(email);
            boolean isExpired = isTokenExpired(token,getSecretKey());
            System.out.println("Extracted email: " + extractedEmail);
            System.out.println("Emails match: " + emailsMatch);
            System.out.println("Token expired: " + isExpired);
            return emailsMatch && !isExpired;
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    private Date extractExpiration(String token, SecretKey key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    public Claims extractClaims(String token, SecretKey key) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            System.out.println("Error extracting claims: " + e.getMessage());
            return null;
        }
    }

    public String generateAccessToken(UserEntity user) {
        Map<String, Object> claims = generateClaims(user);
        return buildToken(claims, user.getEmail(), jwtExpirationMs, secretKey);
    }

    public String generateRefreshToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getEmail());
        return buildToken(claims, user.getEmail(), jwtRefreshExpirationMs, refreshSecretKey);
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiration, SecretKey key) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    private Map<String, Object> generateClaims(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("role", "ROLE_" + user.getRole().name());
        claims.put("packages", List.of("ROLE_" + user.getRole().name()));
        return claims;
    }
}
