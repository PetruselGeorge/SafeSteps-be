package com.example.SafeStep_be.data.access.layer;

import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailReviewEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TrailReviewRepository extends JpaRepository<TrailReviewEntity, UUID> {
    @Query("""
                SELECT r FROM TrailReviewEntity r
                WHERE r.trail.id = :trailId
                ORDER BY 
                    CASE WHEN r.user.id = :userId THEN 0 ELSE 1 END,
                    r.createdAt DESC
            """)
    Page<TrailReviewEntity> findByTrailWithUserFirst(
            @Param("trailId") UUID trailId,
            @Param("userId") UUID userId,
            Pageable pageable
    );

    boolean existsByTrailAndUser(TrailEntity trail, UserEntity user);

    @Query("""
                SELECT AVG(r.rating)
                FROM TrailReviewEntity r
                WHERE r.trail.id = :trailId AND r.rating > 0
            """)
    Double getAverageRatingForTrail(@Param("trailId") UUID trailId);

    @Query("""
                SELECT COUNT(r)
                FROM TrailReviewEntity r
                WHERE r.trail.id = :trailId AND r.rating > 0
            """)
    Long getRatingCountForTrail(@Param("trailId") UUID trailId);
}
