package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.ReverseGeocodingBo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONObject;

@Service
public class ReverseGeocodingBoImplementation implements ReverseGeocodingBo {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getCountryFromCoordinates(double latitude, double longitude) {
        String url = UriComponentsBuilder
                .fromUriString("https://nominatim.openstreetmap.org/reverse")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("format", "json")
                .queryParam("zoom", 10)
                .queryParam("addressdetails", 1)
                .toUriString();
        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);

        if (json.has("address")) {
            JSONObject address = json.getJSONObject("address");
            if (address.has("county")) {
                return address.getString("county");
            } else if (address.has("state")) {
                return address.getString("state");
            }
        }

        return "Unknown";
    }
}
