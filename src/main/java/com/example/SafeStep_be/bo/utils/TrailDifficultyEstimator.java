package com.example.SafeStep_be.bo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrailDifficultyEstimator {
    @Value("${ollama.api.url}")
    private String ollamaApiUrl;

    public String estimateDifficulty(List<LatLng> coordinates, String trailName, BigDecimal distanceKm) {
        List<LatLng> sampledCoords = samplePointsSmart(coordinates);
        String prompt = buildPrompt(sampledCoords, trailName, distanceKm);

        try {
            HttpClient client = HttpClient.newHttpClient();

            String safePrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n");
            String jsonBody = String.format("""
                    {
                      "model": "llama3",
                      "prompt": "%s",
                      "stream": false
                    }
                    """, safePrompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaApiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return extractDifficultyFromResponse(response.body());

        } catch (Exception e) {
            return "Unknown";
        }
    }

    private String buildPrompt(List<LatLng> coords, String name, BigDecimal distanceKm) {
        StringBuilder sb = new StringBuilder();
        sb.append("You are a hiking expert familiar with Romanian mountain trails.\n");
        sb.append("The trail is named \"").append(name).append("\" and is located in Romania.\n");
        sb.append("Its total length is approximately ").append(distanceKm).append(" km.\n");
        sb.append("There is no elevation data, but you can infer relative difficulty from length, sharp turns, and density of points.\n");
        sb.append("The trail is defined by the following GPS coordinates (latitude and longitude):\n");

        for (LatLng point : coords) {
            sb.append("[").append(point.getLat()).append(", ").append(point.getLon()).append("], ");
        }

        sb.append("\n\nBased on your understanding of Romanian terrain and the trail shape, classify the difficulty level.\n");
        sb.append("Use this classification:\n");
        sb.append("- Easy: short, mostly straight or gently winding trail\n");
        sb.append("- Moderate: medium-length with more frequent changes in direction\n");
        sb.append("- Hard: long trail or highly twisted path that could imply more complex terrain\n");
        sb.append("Respond only with one of: Easy, Moderate, Hard.");

        return sb.toString();
    }

    private String extractDifficultyFromResponse(String response) {
        String lower = response.toLowerCase();
        if (lower.contains("easy")) return "Easy";
        if (lower.contains("moderate")) return "Moderate";
        if (lower.contains("hard")) return "Hard";
        return "Unknown";
    }

    private List<LatLng> samplePointsSmart(List<LatLng> original) {
        int total = original.size();
        int everyNth;

        if (total <= 500) {
            everyNth = 1;
        } else if (total <= 1000) {
            everyNth = 2;
        } else if (total <= 3000) {
            everyNth = 5;
        } else if (total <= 6000) {
            everyNth = 10;
        } else {
            everyNth = 25;
        }

        List<LatLng> result = new ArrayList<>();
        for (int i = 0; i < total; i += everyNth) {
            result.add(original.get(i));
        }
        return result;
    }
}
