package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.TrailReviewFacade;
import com.example.SafeStep_be.bo.TrailReviewBo;
import com.example.SafeStep_be.dto.TrailReviewDto;
import com.example.SafeStep_be.mapper.TrailReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TrailReviewFacadeImpl implements TrailReviewFacade {

    private final TrailReviewBo trailReviewBo;
    private final TrailReviewMapper trailReviewMapper;

    @Override
    public TrailReviewDto create(UUID trailId, String token, int rating, String comment) {
        return trailReviewMapper.toDto(
                trailReviewBo.createReview(trailId, token, rating, comment)
        );
    }

    @Override
    public TrailReviewDto update(UUID reviewId, String token, int rating, String comment) {
        return trailReviewMapper.toDto(
                trailReviewBo.updateReview(reviewId, token, rating, comment)
        );
    }

    @Override
    public void delete(UUID reviewId, String token) {
        trailReviewBo.deleteReview(reviewId, token);
    }

    @Override
    public Page<TrailReviewDto> getAll(UUID trailId, String token, Pageable pageable) {
        return trailReviewBo.getReviews(trailId, token, pageable)
                .map(trailReviewMapper::toDto);
    }
}
