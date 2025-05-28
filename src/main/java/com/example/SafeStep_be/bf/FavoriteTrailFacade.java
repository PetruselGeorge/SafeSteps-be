package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.dto.FavoriteTrailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FavoriteTrailFacade {
    FavoriteTrailDto addFavoriteTrail(FavoriteTrailDto dto, String token);

    void deleteFavoriteTrail(UUID trailId, String token);

    Page<FavoriteTrailDto> getAllFavoriteTrails(String token, Pageable pageable);

}
