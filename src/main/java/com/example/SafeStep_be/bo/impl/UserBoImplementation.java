package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.UserBo;
import com.example.SafeStep_be.data.access.layer.UserRepository;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.data.access.layer.enums.Role;
import com.example.SafeStep_be.exceptions.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserBoImplementation implements UserBo {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);

        if (optionalUserEntity.isPresent()) {
            var user = optionalUserEntity.get();
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(String.valueOf(user.getRole()))
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    @Override
    public void createUser(UserEntity user) {
        userRepository.findByEmail(user.getEmail())
                .map(existingUser -> {
                    throw new UserAlreadyExistException(String.format("User with email %s already registered", user.getEmail()));
                })
                .orElseGet(() -> {
                    user.setRole(Role.USER);
                    user.setZonedDateTime(ZonedDateTime.now());
                    return userRepository.save(user);
                });
    }

}
