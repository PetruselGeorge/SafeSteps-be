package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.TrailImageBo;
import com.example.SafeStep_be.data.access.layer.TrailImageRepository;
import com.example.SafeStep_be.data.access.layer.TrailRepository;
import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailImageEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrailImageBoImpl implements TrailImageBo {

    private final TrailImageRepository trailImageRepository;
    private final TrailRepository trailRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TrailImageEntity> getAllImagesByTrailId(UUID trailId) {
        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        return trailImageRepository.findAllByTrailWithTrailFetched(trail);
    }

    @Override
    public void saveTrailImage(UUID trailId, MultipartFile imageFile) {
        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        try {
            TrailImageEntity entity = TrailImageEntity.builder()
                    .trail(trail)
                    .imageBlob(imageFile.getBytes())
                    .build();

            trailImageRepository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

    @Override
    public void deleteImage(UUID trailId, UUID imageId) {
        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        TrailImageEntity image = trailImageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));

        if (!image.getTrail().getId().equals(trail.getId())) {
            throw new IllegalArgumentException("Image does not belong to the specified trail");
        }

        trailImageRepository.delete(image);
    }

}
