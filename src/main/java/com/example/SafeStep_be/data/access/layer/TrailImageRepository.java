package com.example.SafeStep_be.data.access.layer;

import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrailImageRepository extends JpaRepository<TrailImageEntity, UUID> {
    @Query("""
                SELECT ti FROM TrailImageEntity ti
                JOIN FETCH ti.trail
                WHERE ti.trail = :trail
            """)
    List<TrailImageEntity> findAllByTrailWithTrailFetched(TrailEntity trail);

}
