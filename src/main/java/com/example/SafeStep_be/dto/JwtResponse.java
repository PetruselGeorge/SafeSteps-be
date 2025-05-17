package com.example.SafeStep_be.dto;

public record JwtResponse(
        String jwtToken,
        String refreshToken
) {
}
