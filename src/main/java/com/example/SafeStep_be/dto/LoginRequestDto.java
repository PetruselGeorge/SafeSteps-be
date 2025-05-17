package com.example.SafeStep_be.dto;

import com.example.SafeStep_be.util.validation.ValidationRegex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "Email is mandatory")
        @Size(max = 50, message = "Email must not exceed 50 characters")
        @Email(regexp = ValidationRegex.EMAIL_REGEX, message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is mandatory")
        @Size(min = 8, max = 25, message = "Password must be between 8-25 characters")
        @Pattern(regexp = ValidationRegex.PASSWORD_REGEX,
                message = "Password must contain at least one Uppercase letter, one special character (!@#$%^&*), and be 8-25 characters long")
        String password
) {
}
