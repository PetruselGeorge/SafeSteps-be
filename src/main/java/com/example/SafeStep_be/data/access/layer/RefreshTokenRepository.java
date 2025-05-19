package com.example.SafeStep_be.data.access.layer;

import com.example.SafeStep_be.data.access.layer.entities.RefreshTokenEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(UserEntity user);
}
