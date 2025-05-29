package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.WeatherFacade;
import com.example.SafeStep_be.bo.WeatherBo;
import com.example.SafeStep_be.dto.WeatherForecastDto;
import com.example.SafeStep_be.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WeatherFacadeImpl implements WeatherFacade {

    private final WeatherBo weatherBo;
    private final WeatherMapper weatherMapper;

    @Override
    public WeatherForecastDto getForecastForTrail(UUID trailId) {
        JSONObject hourly = weatherBo.getForecastForTrail(trailId);
        return new WeatherForecastDto(weatherMapper.mapHourlyForecast(hourly));
    }
}