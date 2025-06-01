package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.dto.TrailCoordinateDto;

import java.util.List;
import java.util.UUID;

public interface TrailCoordinateFacade {
    List<TrailCoordinateDto> getCoordinatesForTrail(UUID trailId);
}
