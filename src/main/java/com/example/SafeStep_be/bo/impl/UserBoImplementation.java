package com.example.SafeStep_be.bo.impl;

import com.example.SafeStep_be.bo.RefreshTokenBo;
import com.example.SafeStep_be.bo.UserBo;
import com.example.SafeStep_be.data.access.layer.UserRepository;
import com.example.SafeStep_be.data.access.layer.entities.RefreshTokenEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.data.access.layer.enums.Role;
import com.example.SafeStep_be.dto.LoginRequestDto;
import com.example.SafeStep_be.dto.LoginResponseDto;
import com.example.SafeStep_be.exceptions.UserAlreadyExistException;
import com.example.SafeStep_be.util.JwtService;
import com.example.SafeStep_be.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenBo refreshTokenBo;
    private final JwtService jwtService;

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

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.email(),
                        loginRequestDto.password()
                )
        );

        UserEntity user = userRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        refreshTokenBo.deleteByUser(user);

        String jwtToken = jwtTokenUtil.generateAccessToken(user);
        RefreshTokenEntity refreshToken = refreshTokenBo.createRefreshToken(user);
        return new LoginResponseDto(
                jwtToken,
                refreshToken.getToken(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        );
    }


    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String validateRefreshToken(String refreshToken) {
        try {
            System.out.println("Received refresh token: " + refreshToken);

            String email = jwtTokenUtil.extractUsernameFromRefreshToken(refreshToken);
            System.out.println("Extracted email: " + email);

            if (email == null) {
                System.out.println("Failed to extract email from refresh token.");
                return null;
            }

            boolean isValid = jwtService.isRefreshTokenValid(refreshToken, email);
            System.out.println("Is token valid: " + isValid);

            if (isValid) {
                System.out.println("Valid refresh token for email: " + email);
                return email;
            } else {
                System.out.println("Invalid refresh token (validation failed)");
            }
        } catch (Exception e) {
            System.out.println("Invalid refresh token: " + e.getMessage());
        }
        return null;
    }

    @Override
    public LoginResponseDto generateAccessToken(String email) {
        UserEntity user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found for email: " + email);
        }

        user.setTokenVersion(user.getTokenVersion() + 1);
        userRepository.save(user);

        refreshTokenBo.deleteByUser(user);

        String jwtToken = jwtTokenUtil.generateAccessToken(user);
        String newRefreshToken = refreshTokenBo.createRefreshToken(user).getToken();

        return new LoginResponseDto(
                jwtToken,
                newRefreshToken,
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        );
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            String email = jwtTokenUtil.extractUsername(token);
            return jwtTokenUtil.isTokenValid(token, email);
        } catch (Exception e) {
            System.err.println("Error validating token: " + e.getMessage());
            return false;
        }
    }

}
