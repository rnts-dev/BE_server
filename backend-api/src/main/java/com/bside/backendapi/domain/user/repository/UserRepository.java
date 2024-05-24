package com.bside.backendapi.domain.user.repository;

import com.bside.backendapi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUserId(Long userId);
}
