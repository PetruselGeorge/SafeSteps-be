package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.RefreshTokenBo;
import com.example.SafeStep_be.data.access.layer.RefreshTokenRepository;
import com.example.SafeStep_be.data.access.layer.entities.RefreshTokenEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenBoImplementation implements RefreshTokenBo {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.refreshExpiration}")
    private Long refreshTokenDurationMs;

    @Override
    @Transactional
    public RefreshTokenEntity createRefreshToken(UserEntity user) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();

        String token = jwtTokenUtil.generateRefreshToken(user);
        System.out.print(token);
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshTokenDurationMs));
        RefreshTokenEntity savedToken = refreshTokenRepository.save(refreshToken);
        System.out.println("Created Refresh Token: " + savedToken.getToken());
        return savedToken;
    }
    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void deleteByUser(UserEntity user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    public boolean isTokenExpired(RefreshTokenEntity token) {
        return token.getExpiresAt().isBefore(Instant.now());
    }
}
