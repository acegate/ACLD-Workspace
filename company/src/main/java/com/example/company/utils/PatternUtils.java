package com.example.company.utils;

import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class PatternUtils {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public String checkEmail(String email) {
        boolean match = Pattern.matches(EMAIL_PATTERN, email);
        if (match) {
            return email;
        }
        throw new IllegalArgumentException("잘 못된 이메일 형식입니다.");
    }

}
