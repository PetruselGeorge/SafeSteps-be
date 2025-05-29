package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.WeatherForecastDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping(path = WeatherController.ENDPOINT)
public interface WeatherController {

    String ENDPOINT = "/api/weather";

    @GetMapping("/forecast/by-trail/{trailId}")
    ResponseEntity<WeatherForecastDto> getForecastByTrail(@PathVariable UUID trailId);

}
