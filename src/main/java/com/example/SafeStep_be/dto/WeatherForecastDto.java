package com.example.SafeStep_be.dto;

import java.util.List;

public record WeatherForecastDto(
        List<HourlyForecastDto> hourly
) {
    public record HourlyForecastDto(
            String time,
            double temperature,
            double precipitation,
            int weatherCode
    ) {}
}
