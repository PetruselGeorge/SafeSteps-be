package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.TrailReviewController;
import com.example.SafeStep_be.bf.TrailReviewFacade;
import com.example.SafeStep_be.dto.TrailReviewDto;
import com.example.SafeStep_be.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrailReviewControllerImpl implements TrailReviewController {

    private final TrailReviewFacade trailReviewFacade;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity<TrailReviewDto> create(UUID trailId, String authHeader, int rating, String comment) {
        String token = jwtTokenUtil.extractTokenFromHeader(authHeader);
        return ResponseEntity.ok(trailReviewFacade.create(trailId, token, rating, comment));
    }

    @Override
    public ResponseEntity<TrailReviewDto> update(UUID reviewId, String authHeader, int rating, String comment) {
        String token = jwtTokenUtil.extractTokenFromHeader(authHeader);
        return ResponseEntity.ok(trailReviewFacade.update(reviewId, token, rating, comment));
    }

    @Override
    public ResponseEntity<Void> delete(UUID reviewId, String authHeader) {
        String token = jwtTokenUtil.extractTokenFromHeader(authHeader);
        trailReviewFacade.delete(reviewId, token);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<TrailReviewDto>> getAll(UUID trailId, String authHeader, int page, int size) {
        String token = jwtTokenUtil.extractTokenFromHeader(authHeader);
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(trailReviewFacade.getAll(trailId, token, pageable));
    }
}
