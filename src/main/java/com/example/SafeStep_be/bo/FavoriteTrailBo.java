package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.FavoriteTrailEntity;
import com.example.SafeStep_be.dto.FavoriteTrailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FavoriteTrailBo {
    FavoriteTrailEntity addToFavorites(UUID trailId, String token);

    void removeFromFavorites(UUID trailId, String token);

    Page<FavoriteTrailEntity> getAllFavorites(String token, Pageable pageable);
}
