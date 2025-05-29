package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.WeatherController;
import com.example.SafeStep_be.bf.WeatherFacade;
import com.example.SafeStep_be.dto.WeatherForecastDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WeatherControllerImpl implements WeatherController {

    private final WeatherFacade weatherFacade;

    @Override
    public ResponseEntity<WeatherForecastDto> getForecastByTrail(UUID trailId) {
        return ResponseEntity.ok(weatherFacade.getForecastForTrail(trailId));
    }
}
