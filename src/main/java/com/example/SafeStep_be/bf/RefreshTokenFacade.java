package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.data.access.layer.entities.RefreshTokenEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface RefreshTokenFacade {
    RefreshTokenEntity createRefreshToken(UserEntity user);

    Optional<RefreshTokenEntity> findByToken(String token);

    @Transactional
    void deleteByUser(UserEntity user);

    boolean isTokenExpired(RefreshTokenEntity token);

    void deleteToken(String token);
}
