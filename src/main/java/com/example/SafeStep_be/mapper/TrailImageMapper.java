package com.example.SafeStep_be.mapper;

import com.example.SafeStep_be.data.access.layer.entities.TrailImageEntity;
import com.example.SafeStep_be.dto.TrailImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrailImageMapper {
    @Mapping(target = "imageUrl", expression = "java(entity.getTrail() != null ? \"/api/trails/\" + entity.getTrail().getId() + \"/images/\" + entity.getId() : null)")
    TrailImageDto toDto(TrailImageEntity entity);
}
