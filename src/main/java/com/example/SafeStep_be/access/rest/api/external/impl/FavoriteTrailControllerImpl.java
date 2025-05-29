package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.FavoriteTrailController;
import com.example.SafeStep_be.bf.FavoriteTrailFacade;
import com.example.SafeStep_be.dto.FavoriteTrailDto;
import com.example.SafeStep_be.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FavoriteTrailControllerImpl implements FavoriteTrailController {

    private final FavoriteTrailFacade favoriteTrailFacade;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity<FavoriteTrailDto> addFavorite(FavoriteTrailDto dto, String authHeader) {
        String token = jwtTokenUtil.extractTokenFromHeader(authHeader);
        return ResponseEntity.ok(favoriteTrailFacade.addFavoriteTrail(dto, token));
    }

    @Override
    public ResponseEntity<Void> deleteFavorite(UUID trailId, String authHeader) {
        String token = jwtTokenUtil.extractTokenFromHeader(authHeader);
        favoriteTrailFacade.deleteFavoriteTrail(trailId, token);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<FavoriteTrailDto>> getAllFavorites(String authHeader, Pageable pageable) {
        String token = jwtTokenUtil.extractTokenFromHeader(authHeader);
        return ResponseEntity.ok(favoriteTrailFacade.getAllFavoriteTrails(token, pageable));
    }


}
