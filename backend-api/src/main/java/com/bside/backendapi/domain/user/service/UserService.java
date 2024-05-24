package com.bside.backendapi.domain.user.service;

import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public User createTemporaryUser(){
        User tempUser = User.builder()
                .username("tempUser")
                .name("Temporary User")
                .email("tempuser@example.com")
                .profileImg("defaultProfileImg.jpg")
                .thumbnailImg("defaultThumbnailImg.jpg")
                .role("USER")
                .userAppts(new ArrayList<>())
                .receivedPenalties(new ArrayList<>())
                .penalties(new ArrayList<>())
                .build();

        return userRepository.save(tempUser);
    }
}
