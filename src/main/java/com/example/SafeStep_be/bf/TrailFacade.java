package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.dto.TrailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface TrailFacade {
    TrailResponseDto upload(MultipartFile file);

    Page<TrailResponseDto> getAllTrails(Pageable pageable);

    void updateMainImage(UUID trailId, MultipartFile file);

    ResponseEntity<byte[]> getMainImage(UUID trailId);

    Page<TrailResponseDto> searchWithFilters(String name, BigDecimal maxDistance, String difficulty, Pageable pageable);
}
