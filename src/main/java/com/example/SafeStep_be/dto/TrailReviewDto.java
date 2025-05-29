package com.example.SafeStep_be.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TrailReviewDto(
        UUID id,
        UUID trailId,
        String username,
        int rating,
        String comment,
        LocalDateTime createdAt
) {}