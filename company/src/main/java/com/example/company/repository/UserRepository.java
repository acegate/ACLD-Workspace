package com.example.company.repository;

import com.example.company.domain.User;
import com.example.company.domain.key.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserKey> {
    Optional<User> findByEmail(String email);
}
