package com.example.SafeStep_be.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FavoriteTrailDto(
        @NotNull(message = "Trail ID must not be null")
        UUID trailId,

        String name,
        BigDecimal distanceKm,
        String difficulty,
        String location,
        LocalDateTime addedAt,
        String mainImageUrl

) {
}