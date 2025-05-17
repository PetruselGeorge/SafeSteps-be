package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.ProfileController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    @Override
    public String getUserProfile() {
        return "User Profile Data";
    }
}
