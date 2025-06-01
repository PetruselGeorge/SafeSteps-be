package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.TrailAverageRatingDto;
import com.example.SafeStep_be.dto.TrailReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = TrailReviewController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public interface TrailReviewController {
    String ENDPOINT = "api/reviews";

    @PostMapping("/{trailId}")
    ResponseEntity<TrailReviewDto> create(
            @PathVariable UUID trailId,
            @RequestHeader("Authorization") String token,
            @RequestParam int rating,
            @RequestParam String comment
    );

    @PutMapping("/{reviewId}")
    ResponseEntity<TrailReviewDto> update(
            @PathVariable UUID reviewId,
            @RequestHeader("Authorization") String token,
            @RequestParam int rating,
            @RequestParam String comment
    );

    @DeleteMapping("/{reviewId}")
    ResponseEntity<Void> delete(
            @PathVariable UUID reviewId,
            @RequestHeader("Authorization") String token
    );

    @GetMapping("/{trailId}")
    ResponseEntity<Page<TrailReviewDto>> getAll(
            @PathVariable UUID trailId,
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    @GetMapping("/{trailId}/average-rating")
    ResponseEntity<TrailAverageRatingDto> getAverageRating(
            @PathVariable UUID trailId
    );
}
