package com.example.SafeStep_be.util;

import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationMs;
    private final long jwtRefreshExpirationMs;

    public JwtTokenUtil(
            @Value("${jwt.secret_key}") String jwtSecret,
            @Value("${jwt.expiration_time}") long jwtExpirationMs,
            @Value("${jwt.refreshExpiration}") long jwtRefreshExpirationMs) {

        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;

        // Debugging
        System.out.println("Decoded Secret Key (signing): " + Arrays.toString(this.secretKey.getEncoded()));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(extractClaims(token).get("id", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserEntity user) {
        Map<String, Object> claims = generateClaims(user);
        String token = buildToken(claims, user.getEmail(), jwtExpirationMs);
        System.out.println("Generated Token: " + token);
        return token;
    }

    public String generateRefreshToken(String email) {
        return buildToken(new HashMap<>(), email, jwtRefreshExpirationMs);
    }

    public boolean isTokenValid(String token, String email) {
        String username = extractUsername(token);
        return username.equals(email) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiration) {
        System.out.println("Signing with key: " + Arrays.toString(secretKey.getEncoded()));
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractClaims(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        System.out.println("Extracted Claims: " + claims);
        return claims;
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
