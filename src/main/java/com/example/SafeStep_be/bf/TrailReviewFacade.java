package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.dto.TrailReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TrailReviewFacade {
    TrailReviewDto create(UUID trailId, String token, int rating, String comment);

    TrailReviewDto update(UUID reviewId, String token, int rating, String comment);

    void delete(UUID reviewId, String token);

    Page<TrailReviewDto> getAll(UUID trailId, String token, Pageable pageable);
}