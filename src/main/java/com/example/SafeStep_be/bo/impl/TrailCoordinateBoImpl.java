package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.TrailCoordinateBo;
import com.example.SafeStep_be.data.access.layer.TrailCoordinateRepository;
import com.example.SafeStep_be.data.access.layer.entities.TrailCoordinateEntity;
import com.example.SafeStep_be.dto.TrailCoordinateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrailCoordinateBoImpl implements TrailCoordinateBo {

    private final TrailCoordinateRepository repository;


    @Override
    public List<TrailCoordinateEntity> getCoordinatesForTrail(UUID trailId) {
        return repository.findAllByTrailIdOrderByPositionOrderAsc(trailId);
    }
}
