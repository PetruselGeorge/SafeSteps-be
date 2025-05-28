package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.TrailFacade;
import com.example.SafeStep_be.bo.TrailBo;
import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.dto.TrailResponseDto;
import com.example.SafeStep_be.dto.TrailSummaryDto;
import com.example.SafeStep_be.mapper.TrailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TrailFacadeImpl implements TrailFacade {

    private final TrailBo trailBo;
    private final TrailMapper trailMapper;

    @Override
    public TrailResponseDto upload(MultipartFile file) {
        return trailMapper.toDto(trailBo.createTrailFromGpx(file));
    }

    @Override
    public Page<TrailResponseDto> getAllTrails(Pageable pageable) {
        return trailBo.getAllTrails(pageable).map(trailMapper::toDto);
    }

    @Override
    public void updateMainImage(UUID trailId, MultipartFile file) {
        trailBo.updateMainImage(trailId, file);
    }

    @Override
    public ResponseEntity<byte[]> getMainImage(UUID trailId) {
        TrailEntity trail = trailBo.findById(trailId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trail not found"));

        byte[] image = trail.getMainImage();

        if (image == null || image.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No image available for this trail.");
        }

        String mimeType;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not determine image type.");
        }

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(image);
    }

    @Override
    public Page<TrailResponseDto> searchWithFilters(String name, BigDecimal maxDistance, String difficulty, Pageable pageable) {
        return trailBo.searchWithFilters(name, maxDistance, difficulty, pageable)
                .map(trailMapper::fromSummary);
    }
}
