package com.example.company.service;

import com.example.company.domain.User;
import com.example.company.dto.JoinRequest;
import com.example.company.repository.UserRepository;
import com.example.company.utils.PatternUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PatternUtils patternUtils;

    public User joinProcess(JoinRequest joinRequest) {

        String email = joinRequest.getEmail();
        String password = joinRequest.getPassword();
        joinRequest.setPassword(bCryptPasswordEncoder.encode(password));
        String checkEmail = patternUtils.checkEmail(email);
        joinRequest.setEmail(checkEmail);

        boolean existUser = userRepository.findByEmail(email)
                .isPresent();

        if (existUser) {
            throw new IllegalArgumentException("존재하는 유저입니다.");
        }

        User user = new User(joinRequest);
        return userRepository.save(user);
    }
}
