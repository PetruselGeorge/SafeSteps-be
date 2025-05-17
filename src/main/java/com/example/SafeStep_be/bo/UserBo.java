package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.dto.LoginRequestDto;
import com.example.SafeStep_be.dto.LoginResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserBo extends UserDetailsService {
    void createUser(UserEntity user);
    boolean emailExists(String email);
    LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto);

}
