package com.example.SafeStep_be.access.rest.api.external;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = ProfileController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)

public interface ProfileController {
    String ENDPOINT = "/api/user";
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String getUserProfile();
}
