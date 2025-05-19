package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.RefreshTokenFacade;
import com.example.SafeStep_be.bo.RefreshTokenBo;
import com.example.SafeStep_be.data.access.layer.entities.RefreshTokenEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenFacadeImpl implements RefreshTokenFacade {
    private final RefreshTokenBo refreshTokenBo;

    @Override
    public RefreshTokenEntity createRefreshToken(UserEntity user) {
        return refreshTokenBo.createRefreshToken(user);
    }

    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenBo.findByToken(token);
    }

    @Override
    public void deleteByUser(UserEntity user) {
        refreshTokenBo.deleteByUser(user);
    }

    @Override
    public boolean isTokenExpired(RefreshTokenEntity token) {
        return refreshTokenBo.isTokenExpired(token);
    }

    public void deleteToken(String token) {
        refreshTokenBo.findByToken(token).ifPresent(refreshToken -> {
            UserEntity user = refreshToken.getUser();
            refreshTokenBo.deleteByUser(user);
        });
    }
}
