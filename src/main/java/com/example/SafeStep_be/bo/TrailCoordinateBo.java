package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.TrailCoordinateEntity;

import java.util.List;
import java.util.UUID;

public interface TrailCoordinateBo {
    List<TrailCoordinateEntity> getCoordinatesForTrail(UUID trailId);
}
