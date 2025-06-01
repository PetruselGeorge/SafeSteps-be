package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.TrailCoordinateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = TrailCoordinateController.ENDPOINT)
public interface TrailCoordinateController {
    String ENDPOINT = "api/trails";

    @GetMapping("/{trailId}/coordinates")
    ResponseEntity<List<TrailCoordinateDto>> getCoordinatesForTrail(@PathVariable UUID trailId);
}
