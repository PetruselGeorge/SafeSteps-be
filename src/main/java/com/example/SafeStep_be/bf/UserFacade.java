package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.dto.LoginRequestDto;
import com.example.SafeStep_be.dto.LoginResponseDto;
import com.example.SafeStep_be.dto.RegistrationUserDto;
import jakarta.validation.constraints.NotNull;

public interface UserFacade {
    void registerUser(@NotNull RegistrationUserDto registrationUserDto);

    boolean checkEmail(@NotNull String email);

    LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto);

    UserEntity findByEmail(String email);

    String validateRefreshToken(String refreshToken);

    LoginResponseDto generateAccessToken(String email);
}
