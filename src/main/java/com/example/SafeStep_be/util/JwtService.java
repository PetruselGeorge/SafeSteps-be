package com.example.SafeStep_be.util;

import com.example.SafeStep_be.bo.RefreshTokenBo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenBo refreshTokenBo;

    public boolean isRefreshTokenValid(String token, String email) {
        String extractedEmail = jwtTokenUtil.extractUsernameFromRefreshToken(token);
        boolean isExpired = jwtTokenUtil.isTokenExpired(token, jwtTokenUtil.getRefreshSecretKey());

        boolean isActive = refreshTokenBo.findByToken(token)
                .filter(t -> !refreshTokenBo.isTokenExpired(t))
                .isPresent();

        return extractedEmail.equals(email) && !isExpired && isActive;
    }
}