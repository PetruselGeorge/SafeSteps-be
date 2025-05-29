package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.TrailImageFacade;
import com.example.SafeStep_be.bo.TrailImageBo;
import com.example.SafeStep_be.dto.TrailImageDto;
import com.example.SafeStep_be.mapper.TrailImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrailImageFacadeImpl implements TrailImageFacade {

    private final TrailImageBo trailImageBo;
    private final TrailImageMapper trailImageMapper;

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

}
