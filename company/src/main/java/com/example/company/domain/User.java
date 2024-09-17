package com.example.company.domain;

import com.example.company.domain.key.UserKey;
import com.example.company.dto.JoinRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
