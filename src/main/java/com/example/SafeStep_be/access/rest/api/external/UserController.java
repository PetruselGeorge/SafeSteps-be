package com.example.SafeStep_be.access.rest.api.external;

import com.example.SafeStep_be.dto.LoginRequestDto;
import com.example.SafeStep_be.dto.LoginResponseDto;
import com.example.SafeStep_be.dto.RefreshTokenRequest;
import com.example.SafeStep_be.dto.RegistrationUserDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = UserController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserController {
    String ENDPOINT = "/api/auth";

    @PostMapping("/register")
    ResponseEntity<Void> registerUser(@RequestBody @Valid RegistrationUserDto registrationUserDto);

    @GetMapping("/check-email")
    ResponseEntity<Map<String,Boolean>> checkEmail(@RequestParam @Valid String email);

    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto);

    @PostMapping("/refresh")
    ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest);

}
