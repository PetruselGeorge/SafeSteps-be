package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.TrailReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TrailReviewBo {
    TrailReviewEntity createReview(UUID trailId, String token, int rating, String comment);

    TrailReviewEntity updateReview(UUID reviewId, String token, int rating, String comment);

    void deleteReview(UUID reviewId, String token);

    Page<TrailReviewEntity> getReviews(UUID trailId, String token, Pageable pageable);

    Double calculateAverageRating(UUID trailId);

    Long countTotalRatings(UUID trailId);
}
