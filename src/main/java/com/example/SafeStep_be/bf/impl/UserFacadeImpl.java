package com.example.SafeStep_be.bf.impl;

import com.example.SafeStep_be.bf.UserFacade;
import com.example.SafeStep_be.bo.UserBo;
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
        userBo.createUser(userMapper.toEntity(registrationUserDto, passwordEncoder.encode(registrationUserDto.password()),String.valueOf(registrationUserDto.packageType())));
    }
}
