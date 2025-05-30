package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.TrailImageFacade;
import com.example.SafeStep_be.bo.TrailBo;
import com.example.SafeStep_be.bo.TrailImageBo;
import com.example.SafeStep_be.data.access.layer.entities.TrailImageEntity;
import com.example.SafeStep_be.dto.TrailImageDto;
import com.example.SafeStep_be.mapper.TrailImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrailImageFacadeImpl implements TrailImageFacade {

    private final TrailImageBo trailImageBo;
    private final TrailImageMapper trailImageMapper;
    private final TrailBo trailBo;

    @Override
    public List<TrailImageDto> getAllImagesForTrail(UUID trailId) {
        return trailImageBo.getAllImagesByTrailId(trailId).stream()
                .map(trailImageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void uploadImage(UUID trailId, MultipartFile imageFile) {
        trailImageBo.saveTrailImage(trailId, imageFile);
    }

    @Override
    public void deleteImage(UUID trailId, UUID imageId) {
        trailImageBo.deleteImage(trailId, imageId);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getImageResponse(UUID trailId, UUID imageId) {
        TrailImageEntity imageEntity = trailImageBo.findByTrailIdAndImageId(trailId, imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));

        byte[] data = imageEntity.getImageBlob();

        if (data == null || data.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empty image");
        }

        String mimeType;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot detect MIME type");
        }

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(data);
    }

}
