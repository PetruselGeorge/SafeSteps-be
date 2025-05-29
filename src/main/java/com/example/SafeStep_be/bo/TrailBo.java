package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.dto.TrailSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrailBo {

    TrailEntity createTrailFromGpx(MultipartFile gpxFile);

    Page<TrailEntity> getAllTrails(Pageable pageable);

    void updateMainImage(UUID trailId, MultipartFile file);

    Optional<TrailEntity> findById(UUID trailId);

    Page<TrailSummaryDto> searchWithFilters(String name, BigDecimal maxDistance, String difficulty, Pageable pageable);

}
