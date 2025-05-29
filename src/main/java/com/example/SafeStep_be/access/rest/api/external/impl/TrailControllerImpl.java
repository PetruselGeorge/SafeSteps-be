package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.TrailController;
import com.example.SafeStep_be.bf.TrailFacade;
import com.example.SafeStep_be.dto.TrailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrailControllerImpl implements TrailController {
    private final TrailFacade trailFacade;

    @Override
    public ResponseEntity<TrailResponseDto> uploadTrail(MultipartFile file) {
        return ResponseEntity.ok(trailFacade.upload(file));
    }

    @Override
    public ResponseEntity<Page<TrailResponseDto>> getAllTrails(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createdAt").descending());
        return ResponseEntity.ok(trailFacade.getAllTrails(pageable));
    }

    @Override
    public ResponseEntity<Void> updateMainImage(UUID trailId, MultipartFile file) {
        trailFacade.updateMainImage(trailId, file);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<byte[]> getMainImage(UUID trailId) {
        return trailFacade.getMainImage(trailId);
    }

    @Override
    public ResponseEntity<Page<TrailResponseDto>> searchWithFilters(String name, BigDecimal maxDistance, String difficulty, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TrailResponseDto> result = trailFacade.searchWithFilters(name, maxDistance, difficulty, pageable);
        return ResponseEntity.ok(result);
    }
}
