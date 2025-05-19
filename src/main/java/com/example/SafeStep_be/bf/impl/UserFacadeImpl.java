package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.UserFacade;
import com.example.SafeStep_be.bo.UserBo;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.dto.LoginRequestDto;
import com.example.SafeStep_be.dto.LoginResponseDto;
import com.example.SafeStep_be.dto.RegistrationUserDto;
import com.example.SafeStep_be.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserMapper userMapper;
    private final UserBo userBo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegistrationUserDto registrationUserDto) {
        userBo.createUser(userMapper.toEntity(registrationUserDto, passwordEncoder.encode(registrationUserDto.password()), String.valueOf(registrationUserDto.packageType())));
    }

    @Override
    public boolean checkEmail(String email) {
        return userBo.emailExists(email);
    }

    @Override
    public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) {
        return userBo.authenticateUser(loginRequestDto);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userBo.findByEmail(email);
    }

    @Override
    public String validateRefreshToken(String refreshToken) {
        String email = userBo.validateRefreshToken(refreshToken);
        if (email == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        return email;
    }

    @Override
    public LoginResponseDto generateAccessToken(String email) {
        return userBo.generateAccessToken(email);
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        return userBo.isTokenValid(accessToken);
    }
}
