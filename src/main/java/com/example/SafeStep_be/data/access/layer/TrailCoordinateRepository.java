package com.example.SafeStep_be.data.access.layer;

import com.example.SafeStep_be.data.access.layer.entities.TrailCoordinateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrailCoordinateRepository extends JpaRepository<TrailCoordinateEntity, UUID> {
    List<TrailCoordinateEntity> findAllByTrailIdOrderByPositionOrderAsc(UUID trailId);
}

