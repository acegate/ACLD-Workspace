package com.example.company.controller;

import com.example.company.dto.JoinRequest;
import com.example.company.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class JoinTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원가입 테스트")
    @Test
    public void joinTest() throws Exception {
        // given
        final String url = "/signup";

        JoinRequest request = JoinRequest.builder()
                .email("test@naver.com")
                .password("1234")
                .build();

        final String requestBody = objectMapper.writeValueAsString(request);


        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isCreated());
    }

    @DisplayName("로그인 테스트")
    @Test
    public void loginTest() throws Exception {
        // given
        final String url = "/login";

        JoinRequest request = JoinRequest.builder()
                .email("test@naver.com")
                .password("1234")
                .build();

        final String requestBody = objectMapper.writeValueAsString(request);


        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isCreated());
    }


}
