package com.example.company.controller;

import com.example.company.domain.User;
import com.example.company.dto.AuthResponse;
import com.example.company.dto.LoginRequest;
import com.example.company.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {

    private final TokenProvider tokenProvider;

    @GetMapping
    public String login(){
        return "login";
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        // 사용자 인증 로직 (여기서는 예시로 간단한 조건 사용)
//        Duration tokenValidity = Duration.ofHours(1);
//        if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
//
//
//            tokenProvider.generateToken(tokenValidity, new User("sdf", "asdad"));
//            String token = jwtUtil.generateToken(loginRequest.getUsername());
//            return ResponseEntity.ok(new AuthResponse(token));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }


}
