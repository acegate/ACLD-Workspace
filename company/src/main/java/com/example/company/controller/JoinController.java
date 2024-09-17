package com.example.company.controller;

import com.example.company.domain.User;
import com.example.company.dto.JoinRequest;
import com.example.company.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/signup")
    public ResponseEntity<User> joinProcess(@RequestBody JoinRequest joinRequest) {
        User createdUser = joinService.joinProcess(joinRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser);
    }
}
