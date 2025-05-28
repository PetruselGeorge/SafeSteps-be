package com.example.SafeStep_be.mapper;

import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import com.example.SafeStep_be.dto.TrailResponseDto;
import com.example.SafeStep_be.dto.TrailSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TrailMapper {
    @Mapping(target = "mainImageUrl", expression = "java(\"/api/trails/\" + entity.getId() + \"/main-image\")")
    TrailResponseDto toDto(TrailEntity entity);

    @Mapping(target = "mainImageUrl", expression = "java(\"/api/trails/\" + summary.id() + \"/main-image\")")
    TrailResponseDto fromSummary(TrailSummaryDto summary);
}