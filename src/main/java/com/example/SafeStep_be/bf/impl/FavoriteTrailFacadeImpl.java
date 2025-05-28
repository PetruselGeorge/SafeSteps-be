package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.FavoriteTrailFacade;
import com.example.SafeStep_be.bo.FavoriteTrailBo;
import com.example.SafeStep_be.data.access.layer.entities.FavoriteTrailEntity;
import com.example.SafeStep_be.dto.FavoriteTrailDto;
import com.example.SafeStep_be.mapper.FavoriteTrailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FavoriteTrailFacadeImpl implements FavoriteTrailFacade {

    private final FavoriteTrailBo favoriteTrailBo;
    private final FavoriteTrailMapper favoriteTrailMapper;

    @Override
    public FavoriteTrailDto addFavoriteTrail(FavoriteTrailDto dto, String token) {
        FavoriteTrailEntity entity = favoriteTrailBo.addToFavorites(dto.trailId(), token);
        return favoriteTrailMapper.toDto(entity);
    }

    @Override
    public void deleteFavoriteTrail(UUID trailId, String token) {
        favoriteTrailBo.removeFromFavorites(trailId, token);
    }

    @Override
    public Page<FavoriteTrailDto> getAllFavoriteTrails(String token, Pageable pageable) {
        return favoriteTrailBo.getAllFavorites(token, pageable).map(favoriteTrailMapper::toDto);
    }
}
