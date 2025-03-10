package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.RegistrationUserDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = UserController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserController {
    String ENDPOINT = "/api/auth";

    @PostMapping("/register")
    ResponseEntity<Void> registerUser(@RequestBody @Valid RegistrationUserDto registrationUserDto);

}
