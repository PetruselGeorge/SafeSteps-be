    package com.example.SafeStep_be.mapper;

    import com.example.SafeStep_be.data.access.layer.entities.TrailCoordinateEntity;
    import com.example.SafeStep_be.dto.TrailCoordinateDto;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;

    import java.util.List;

    @Mapper(componentModel = "spring")
    public interface TrailCoordinateMapper {

        @Mapping(source = "id", target = "id")
        @Mapping(source = "latitude", target = "latitude")
        @Mapping(source = "longitude", target = "longitude")
        @Mapping(source = "positionOrder", target = "positionOrder")
        @Mapping(source = "segmentIndex", target = "segmentIndex")
        TrailCoordinateDto toDto(TrailCoordinateEntity entity);

        List<TrailCoordinateDto> toDtoList(List<TrailCoordinateEntity> entities);
    }
