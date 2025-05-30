package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.dto.TrailImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TrailImageFacade {
    List<TrailImageDto> getAllImagesForTrail(UUID trailId);

    void uploadImage(UUID trailId, MultipartFile imageFile);

    void deleteImage(UUID trailId, UUID imageId);
    ResponseEntity<byte[]> getImageResponse(UUID trailId, UUID imageId);
}
