package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.TrailImageController;
import com.example.SafeStep_be.bf.TrailImageFacade;
import com.example.SafeStep_be.dto.TrailImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrailImageControllerImpl implements TrailImageController {

    private final TrailImageFacade trailImageFacade;

    @Override
    public ResponseEntity<List<TrailImageDto>> getAllImagesForTrail(UUID trailId) {
        try {
            List<TrailImageDto> images = trailImageFacade.getAllImagesForTrail(trailId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> uploadImage(UUID trailId, MultipartFile imageFile) {
        trailImageFacade.uploadImage(trailId, imageFile);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteImage(UUID trailId, UUID imageId) {
        trailImageFacade.deleteImage(trailId, imageId);
        return ResponseEntity.noContent().build();
    }
}
