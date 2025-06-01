package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bo.TrailCoordinateBo;
import com.example.SafeStep_be.dto.TrailCoordinateDto;
import com.example.SafeStep_be.bf.TrailCoordinateFacade;
import com.example.SafeStep_be.mapper.TrailCoordinateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrailCoordinateFacadeImpl implements TrailCoordinateFacade {

    private final TrailCoordinateBo trailCoordinateBo;
    private final TrailCoordinateMapper trailCoordinateMapper;

    @Override
    public List<TrailCoordinateDto> getCoordinatesForTrail(UUID trailId) {
        return trailCoordinateMapper.toDtoList(trailCoordinateBo.getCoordinatesForTrail(trailId));
    }
}
