package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.TrailImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = TrailImageController.ENDPOINT)
public interface TrailImageController {
    String ENDPOINT = "/api/trails";

    @GetMapping("/{trailId}/images")
    ResponseEntity<List<TrailImageDto>> getAllImagesForTrail(@PathVariable UUID trailId);

    @PostMapping("/{trailId}/images")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Void> uploadImage(@PathVariable UUID trailId,
                                     @RequestPart("image") MultipartFile imageFile);

    @DeleteMapping("/{trailId}/images/{imageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Void> deleteImage(@PathVariable UUID trailId, @PathVariable UUID imageId);

    @GetMapping("/{trailId}/images/{imageId}")
    ResponseEntity<byte[]> getAdditionalImage(
            @PathVariable UUID trailId,
            @PathVariable UUID imageId);

}