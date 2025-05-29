package com.example.SafeStep_be.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TrailResponseDto(
        @NotNull(message = "ID is required")
        UUID id,

        @NotBlank(message = "Trail name must not be blank")
        String name,

        @NotNull(message = "Distance must not be null")
        @DecimalMin(value = "0.01", message = "Distance must be greater than 0")
        BigDecimal distanceKm,

        @NotBlank(message = "Difficulty must not be blank")
        String difficulty,

        String location,


        String mainImageUrl

) {}
