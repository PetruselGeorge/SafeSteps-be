package com.example.SafeStep_be.dto;

import java.util.UUID;

public record TrailCoordinateDto(
        UUID id,
        double latitude,
        double longitude,
        int positionOrder,
        int segmentIndex
) {}
