package com.example.company.domain;

import com.example.company.domain.key.UserKey;
import com.example.company.dto.JoinRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;

@Getter
@Entity
@Table(name = "users")
@IdClass(UserKey.class)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_no")
    private Long user_no;
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "pwd")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    public User(JoinRequest joinRequest) {
        this.email = joinRequest.getEmail();
        this.password = joinRequest.getPassword();
        this.userRole = UserRole.ROLE_ADMIN;
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.userRole = findUserRole(role);
    }

    public User(String email, String role) {
        this.email = email;
        this.userRole = findUserRole(role);
    }

    private UserRole findUserRole(String role) {
        return Arrays.stream(UserRole.values())
                .filter(item -> item.name().equals(role))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

}
