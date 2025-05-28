package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.TrailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@RequestMapping(path = TrailController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public interface TrailController {
    String ENDPOINT = "api/trails";

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<TrailResponseDto> uploadTrail(@RequestParam("file") MultipartFile file);

    @GetMapping
    public ResponseEntity<Page<TrailResponseDto>> getAllTrails(@RequestParam(defaultValue = "0") int page);

    @PatchMapping(value = "/{trailId}/main-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<Void> updateMainImage(
            @PathVariable UUID trailId,
            @RequestParam("file") MultipartFile file);


    @GetMapping(value = "/{trailId}/main-image")
    ResponseEntity<byte[]> getMainImage(@PathVariable UUID trailId);

    @GetMapping("/search-with-filters")
    public ResponseEntity<Page<TrailResponseDto>> searchWithFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal maxDistance,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

}
