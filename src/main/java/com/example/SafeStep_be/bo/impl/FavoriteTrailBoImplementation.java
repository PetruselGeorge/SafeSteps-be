package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.FavoriteTrailBo;
import com.example.SafeStep_be.data.access.layer.FavoriteTrailRepository;
import com.example.SafeStep_be.data.access.layer.TrailRepository;
import com.example.SafeStep_be.data.access.layer.UserRepository;
import com.example.SafeStep_be.data.access.layer.entities.FavoriteTrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.util.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteTrailBoImplementation implements FavoriteTrailBo {
    private final FavoriteTrailRepository favoriteTrailRepository;
    private final TrailRepository trailRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public FavoriteTrailEntity addToFavorites(UUID trailId, String token) {
        UUID userId = jwtTokenUtil.extractUserId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        if (favoriteTrailRepository.existsByUser_IdAndTrail_Id(userId, trailId)) {
            throw new IllegalStateException("Trail already in favorites");
        }

        FavoriteTrailEntity favorite = FavoriteTrailEntity.builder()
                .user(user)
                .trail(trail)
                .addedAt(LocalDateTime.now())
                .build();

        return favoriteTrailRepository.save(favorite);
    }

    @Override
    public void removeFromFavorites(UUID trailId, String token) {
        UUID userId = jwtTokenUtil.extractUserId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        FavoriteTrailEntity favorite = favoriteTrailRepository.findByUserAndTrail(user, trail)
                .orElseThrow(() -> new EntityNotFoundException("Trail not in favorites."));

        favoriteTrailRepository.delete(favorite);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FavoriteTrailEntity> getAllFavorites(String token, Pageable pageable) {
        UUID userID = jwtTokenUtil.extractUserId(token);

        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return favoriteTrailRepository.findAllByUserWithTrail(user, pageable);
    }
}
