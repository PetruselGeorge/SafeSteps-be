package com.example.SafeStep_be.mapper;

import com.example.SafeStep_be.data.access.layer.entities.TrailReviewEntity;
import com.example.SafeStep_be.dto.TrailReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrailReviewMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "trail.id", target = "trailId")
    @Mapping(source = "user.firstName", target = "username")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "comment", target = "comment")
    @Mapping(source = "createdAt", target = "createdAt")
    TrailReviewDto toDto(TrailReviewEntity entity);
}