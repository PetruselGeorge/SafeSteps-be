package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.TrailCoordinateController;
import com.example.SafeStep_be.dto.TrailCoordinateDto;
import com.example.SafeStep_be.bf.TrailCoordinateFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrailCoordinateControllerImpl implements TrailCoordinateController {

    private final TrailCoordinateFacade trailCoordinateFacade;

    @Override
    public ResponseEntity<List<TrailCoordinateDto>> getCoordinatesForTrail(UUID trailId) {
        return ResponseEntity.ok(trailCoordinateFacade.getCoordinatesForTrail(trailId));
    }
}
