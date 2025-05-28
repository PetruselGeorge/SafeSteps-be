package com.example.SafeStep_be.data.access.layer;

import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.dto.TrailSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface TrailRepository extends JpaRepository<TrailEntity, UUID> {

    @Query("""
    SELECT new com.example.SafeStep_be.dto.TrailSummaryDto(t.id, t.name, t.distanceKm, t.difficulty)
    FROM TrailEntity t
    WHERE (:name IS NULL OR :name = '' OR
           cast(function('unaccent', lower(t.name)) as string) LIKE cast(function('unaccent', lower(concat('%', :name, '%'))) as string))
      AND (:maxDistance IS NULL OR t.distanceKm <= :maxDistance)
      AND (:difficulty IS NULL OR t.difficulty = :difficulty)
""")
    Page<TrailSummaryDto> searchWithFilters(
            @Param("name") String name,
            @Param("maxDistance") BigDecimal maxDistance,
            @Param("difficulty") String difficulty,
            Pageable pageable
    );
}
