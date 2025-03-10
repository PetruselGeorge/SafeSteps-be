package com.example.SafeStep_be.access.rest.api.external.impl;

import com.example.SafeStep_be.access.rest.api.external.UserController;
import com.example.SafeStep_be.bf.UserFacade;
import com.example.SafeStep_be.dto.RegistrationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserFacade userFacade;

    @Override
    public ResponseEntity<Void> registerUser(RegistrationUserDto registrationUserDto) {
        userFacade.registerUser(registrationUserDto);
        return ResponseEntity.ok().build();
    }
}
