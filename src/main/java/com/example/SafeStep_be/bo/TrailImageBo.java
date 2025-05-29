package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.TrailImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TrailImageBo {
    List<TrailImageEntity> getAllImagesByTrailId(UUID trailId);

    void saveTrailImage(UUID trailId, MultipartFile imageFile);

    void deleteImage(UUID trailId, UUID imageId);

}
