package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.TrailReviewBo;
import com.example.SafeStep_be.data.access.layer.TrailRepository;
import com.example.SafeStep_be.data.access.layer.TrailReviewRepository;
import com.example.SafeStep_be.data.access.layer.UserRepository;
import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailReviewEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrailReviewBoImplementation implements TrailReviewBo {

    private final TrailRepository trailRepository;
    private final TrailReviewRepository trailReviewRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public TrailReviewEntity createReview(UUID trailId, String token, int rating, String comment) {
        UUID userId = jwtTokenUtil.extractUserId(token);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        TrailReviewEntity review = TrailReviewEntity.builder()
                .trail(trail)
                .user(user)
                .rating(rating)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .build();

        return trailReviewRepository.save(review);
    }

    @Override
    public TrailReviewEntity updateReview(UUID reviewId, String token, int rating, String comment) {
        UUID userId = jwtTokenUtil.extractUserId(token);
        TrailReviewEntity review = trailReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new SecurityException("You can only update your own reviews.");
        }

        review.setRating(rating);
        review.setComment(comment);
        return trailReviewRepository.save(review);
    }

    @Override
    public void deleteReview(UUID reviewId, String token) {
        UUID userId = jwtTokenUtil.extractUserId(token);
        TrailReviewEntity review = trailReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new SecurityException("You can only delete your own reviews.");
        }

        trailReviewRepository.delete(review);
    }

    @Override
    public Page<TrailReviewEntity> getReviews(UUID trailId, String token, Pageable pageable) {
        UUID userId = jwtTokenUtil.extractUserId(token);
        return trailReviewRepository.findByTrailWithUserFirst(trailId, userId, pageable);
    }
}
