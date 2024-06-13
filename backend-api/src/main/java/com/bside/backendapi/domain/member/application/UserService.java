//package com.bside.backendapi.domain.member.application;
//
//import com.bside.backendapi.domain.member.entity.User;
//import com.bside.backendapi.domain.member.repository.UserRepository;
//import com.bside.backendapi.global.jwt.util.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class UserService {
//    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
//
//    public User createTemporaryUser(){
//        User tempUser = User.builder()
//                .username("tempUser")
//                .profileImg("defaultProfileImg.jpg")
//                .thumbnailImg("defaultThumbnailImg.jpg")
//                .role("USER")
//                .userAppts(new ArrayList<>())
//                .receivedPenalties(new ArrayList<>())
//                .penalties(new ArrayList<>())
//                .build();
//
//        return userRepository.save(tempUser);
//    }
//
//    public String creatUserToken(Long id){
//
//        User user = userRepository.findById(id).orElseThrow();
//        return jwtUtil.createJwt(String.valueOf(user.getId()), user.getRole(), 3600000L); // 1 hour expiration
//    }
//
//    public void getTendency(String tendency, HttpServletRequest httpRequest) {
//        String token = jwtUtil.extractTokenFromHeader(httpRequest);
//        log.info("appointment token {}", token);
//        String userIdString = jwtUtil.getUserId(token);
//        log.info("userIdString {}", userIdString);
//        Long userId = Long.parseLong(userIdString);        //현재 사용자
//        log.info("userid {}", userId);
//
//        User user = userRepository.findById(userId).orElseThrow();
//        user.setTendency(tendency);
//    }
//}
