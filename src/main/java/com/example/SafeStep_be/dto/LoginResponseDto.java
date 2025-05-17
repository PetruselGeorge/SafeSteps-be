package com.example.SafeStep_be.dto;

public record LoginResponseDto(
        String jwtToken,
        String refreshToken,
        String firstName,
        String lastName,
        String role
) {
}
