package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.dto.WeatherForecastDto;

import java.util.UUID;

public interface WeatherFacade {
    WeatherForecastDto getForecastForTrail(UUID trailId);
}
