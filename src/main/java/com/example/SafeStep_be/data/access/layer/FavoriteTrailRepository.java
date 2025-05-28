package com.example.SafeStep_be.data.access.layer;

import com.example.SafeStep_be.data.access.layer.entities.FavoriteTrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FavoriteTrailRepository extends JpaRepository<FavoriteTrailEntity, UUID> {
    Page<FavoriteTrailEntity> findAllByUser(UserEntity user, Pageable pageable);

    boolean existsByUser_IdAndTrail_Id(UUID userId, UUID trailId);

    Optional<FavoriteTrailEntity> findByUserAndTrail(UserEntity user, TrailEntity trail);


    @Query("""
                SELECT f FROM FavoriteTrailEntity f
                JOIN FETCH f.trail t
                WHERE f.user = :user
            """)
    Page<FavoriteTrailEntity> findAllByUserWithTrail(UserEntity user, Pageable pageable);
}
