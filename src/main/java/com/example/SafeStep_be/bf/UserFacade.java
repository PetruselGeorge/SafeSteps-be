package com.example.SafeStep_be.bf;

import com.example.SafeStep_be.dto.RegistrationUserDto;
import jakarta.validation.constraints.NotNull;

public interface UserFacade {
    void registerUser(@NotNull RegistrationUserDto registrationUserDto);
}
