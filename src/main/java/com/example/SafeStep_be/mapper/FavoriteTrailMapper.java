package com.example.SafeStep_be.mapper;

import com.example.SafeStep_be.data.access.layer.entities.FavoriteTrailEntity;
import com.example.SafeStep_be.dto.FavoriteTrailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteTrailMapper {
    @Mapping(source = "trail.id", target = "trailId")
    @Mapping(source = "trail.name", target = "name")
    @Mapping(source = "trail.distanceKm", target = "distanceKm")
    @Mapping(source = "trail.difficulty", target = "difficulty")
    @Mapping(source = "addedAt", target = "addedAt")
    @Mapping(target = "mainImageUrl", expression = "java(\"/api/trails/\" + entity.getTrail().getId() + \"/main-image\")")
    FavoriteTrailDto toDto(FavoriteTrailEntity entity);
}
