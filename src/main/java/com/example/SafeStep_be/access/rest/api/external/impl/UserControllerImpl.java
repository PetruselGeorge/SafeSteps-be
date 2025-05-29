package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.UserController;
import com.example.SafeStep_be.bf.UserFacade;
import com.example.SafeStep_be.dto.LoginRequestDto;
import com.example.SafeStep_be.dto.LoginResponseDto;
import com.example.SafeStep_be.dto.RefreshTokenRequest;
import com.example.SafeStep_be.dto.RegistrationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserFacade userFacade;

    @Override
    public ResponseEntity<Void> registerUser(RegistrationUserDto registrationUserDto) {
        userFacade.registerUser(registrationUserDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> checkEmail(String email) {
        return ResponseEntity.ok(Map.of("exists", userFacade.checkEmail(email)));
    }

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponse = userFacade.authenticateUser(loginRequestDto);
        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken().trim();

        String email = userFacade.validateRefreshToken(refreshToken);

        if (email == null) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        LoginResponseDto newAccessToken = userFacade.generateAccessToken(email);
        return ResponseEntity.ok(newAccessToken);
    }

    @Override
    public ResponseEntity<Boolean> validateAccessToken(String accessToken) {
        try {
            if (accessToken != null && accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.substring(7);
            }

            boolean isValid = userFacade.validateAccessToken(accessToken);

            if (!isValid) {
                return ResponseEntity.ok(false);
            }

            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

}
