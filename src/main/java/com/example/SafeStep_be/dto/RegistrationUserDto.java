package com.example.SafeStep_be.dto;

import com.example.SafeStep_be.data.access.layer.enums.PackageType;
import com.example.SafeStep_be.util.validation.ValidationRegex;
import com.example.SafeStep_be.util.validation.ValidationUtil;
import jakarta.validation.constraints.*;

public record RegistrationUserDto(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(regexp = ValidationRegex.EMAIL_REGEX, message = "must be valid")
        String email,

        @NotBlank(message = "is mandatory")
        @Size(min = 8, max = 25, message = "must be between 8-25 characters.")
        @Pattern(regexp = ValidationRegex.PASSWORD_REGEX,
                message = "must contain at least one Uppercase letter, a special" +
                        "character (!@#$%^&*)")
        String password,

        @NotBlank(message = "is mandatory")
        @Size(min = 8, max = 25, message = "must be between 8-25 characters.")
        @Pattern(regexp = ValidationRegex.PASSWORD_REGEX,
                message = "must contain at least one Uppercase letter, a special" +
                        "character (!@#$%^&*) and a length between 8-25")
        String confirmPassword,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Country is required")
        String country,

        @NotNull(message = "Package type is required (BASIC, PREMIUM)")
        PackageType packageType
) {
    @AssertTrue(message = "must match")
    private boolean isPasswords() {
        return ValidationUtil.isConfirmPassword(confirmPassword, password);
    }
}
