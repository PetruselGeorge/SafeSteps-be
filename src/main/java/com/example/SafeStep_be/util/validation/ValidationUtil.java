package com.example.SafeStep_be.util.validation;

public class ValidationUtil {

    public static boolean isConfirmPassword(String confirmPassword, String password) {
        return password.equals(confirmPassword);
    }

}
