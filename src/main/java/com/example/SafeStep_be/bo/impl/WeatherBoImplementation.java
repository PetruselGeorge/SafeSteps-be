package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.WeatherBo;
import com.example.SafeStep_be.data.access.layer.TrailRepository;
import com.example.SafeStep_be.data.access.layer.entities.TrailCoordinateEntity;
import com.example.SafeStep_be.data.access.layer.entities.TrailEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeatherBoImplementation implements WeatherBo {

    private final WebClient webClient = WebClient.create("https://api.open-meteo.com");
    private final TrailRepository trailRepository;

    @Override
    public JSONObject getRawForecast(double lat, double lon) {
        String json = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("hourly", "temperature_2m,precipitation,weathercode")
                        .queryParam("timezone", "auto")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return new JSONObject(json).getJSONObject("hourly");
    }

    @Override
    public JSONObject getForecastForTrail(UUID trailId) {
        TrailEntity trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new EntityNotFoundException("Trail not found"));

        List<TrailCoordinateEntity> coords = trail.getCoordinates().stream()
                .sorted(Comparator.comparingInt(TrailCoordinateEntity::getPositionOrder))
                .limit(2)
                .toList();

        if (coords.isEmpty()) {
            throw new IllegalArgumentException("Trail has no coordinates.");
        }

        double lat = coords.get(0).getLatitude();
        double lon = coords.get(0).getLongitude();

        return getRawForecast(lat, lon);
    }
}
