package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.dto.WeatherForecastDto;
import org.json.JSONObject;

import java.util.UUID;

public interface WeatherBo {

    JSONObject getRawForecast(double lat, double lon);

    JSONObject getForecastForTrail(UUID trailId);
}
