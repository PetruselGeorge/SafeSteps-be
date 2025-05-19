package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.RefreshTokenEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;

import java.util.Optional;

public interface RefreshTokenBo {
    RefreshTokenEntity createRefreshToken(UserEntity user);
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(UserEntity user);
    boolean isTokenExpired(RefreshTokenEntity token);
}
