package com.example.SafeStep_be.bo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrailDifficultyEstimator {
    @Value("${huggingface.api.key}")
    private String hfApiKey;
}
