package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.FavoriteTrailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = FavoriteTrailController.ENDPOINT)
public interface FavoriteTrailController {
    String ENDPOINT = "/api/favorites";

    @PostMapping
    ResponseEntity<FavoriteTrailDto> addFavorite(@RequestBody FavoriteTrailDto dto,
                                                 @RequestHeader("Authorization") String authHeader);

    @DeleteMapping("/{trailId}")
    ResponseEntity<Void> deleteFavorite(@PathVariable UUID trailId,
                                        @RequestHeader("Authorization") String authHeader);

    @GetMapping
    ResponseEntity<Page<FavoriteTrailDto>> getAllFavorites(@RequestHeader("Authorization") String authHeader,
                                                           Pageable pageable);
}
