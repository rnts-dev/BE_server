package com.bside.backendapi.domain.user.service;

import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

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

    public String creatUserToken(Long id){

        User user = userRepository.findById(id).orElseThrow();
        return jwtUtil.createJwt(String.valueOf(user.getId()), user.getRole(), 3600000L); // 1 hour expiration
    }

}
