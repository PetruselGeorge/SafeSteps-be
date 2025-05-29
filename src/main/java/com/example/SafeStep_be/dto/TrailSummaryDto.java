package com.example.SafeStep_be.dto;


import java.math.BigDecimal;
import java.util.UUID;

public record TrailSummaryDto(
        UUID id,
        String name,
        BigDecimal distanceKm,
        String difficulty,
        String location
) {
}
