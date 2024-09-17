package com.example.company.utils;

import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class PatternUtils {
    private static final String EMAIL_PATTERN = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

    public String checkEmail(String email) {
        boolean match = Pattern.matches(EMAIL_PATTERN, email);
        if (match) {
            return email;
        }
        throw new IllegalArgumentException("잘 못된 이메일 형식입니다.");
    }

}
