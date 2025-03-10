package com.example.SafeStep_be.util.validation;

public class ValidationRegex {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}\\[\\]:\";'<>?,./~`]).+$";
    public static final String CURRENCY_CODE_REGEX = "^[A-Z]{3}$";
}
