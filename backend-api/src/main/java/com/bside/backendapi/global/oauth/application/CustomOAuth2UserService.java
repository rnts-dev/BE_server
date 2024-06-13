package com.bside.backendapi.global.oauth.application;//package com.bside.backendapi.global.oauth.service;
//
//import com.bside.backendapi.domain.user.dto.UserDTO;
//import com.bside.backendapi.domain.user.entity.User;
//import com.bside.backendapi.domain.user.repository.UserRepository;
//import com.bside.backendapi.global.oauth.dto.CustomOAuth2User;
//import com.bside.backendapi.global.oauth.dto.KakaoResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Map;
//
//@Slf4j
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        log.info("---------------------------------- loadUser() ----------------------------------");
//        log.info("oAuth2User : {}", oAuth2User);
//        log.info("attributes : {}", attributes);
//
//        KakaoResponse kakaoResponse = new KakaoResponse(attributes);
//
//        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 "kakao 사용자 아이디" 값을 만듬
//        String username = "kakao " + kakaoResponse.getProviderId();
//
//        // 프로필 이미지, 썸네일 이미지
//        String profileImg = String.valueOf(kakaoResponse.getProfile().get("profile_image_url"));
//        String thumbnailImageUrl = kakaoResponse.getProfile().get("thumbnail_image_url").toString();
//
//        // 넘어온 회원정보가 있는지 확인
//        User existUser = userRepository.findByUsername(username);
//
//        // 존재하지 않는다면 회원정보를 저장하고 CustomOAuth2User 반환
//        if (existUser == null) {
//            // 사용자가 없다면 생성해서 DB 저장
//            User user = User.builder()
//                    .username(username)
//                    .name(kakaoResponse.getName())
////                    .email(kakaoResponse.getEmail())
//                    .profileImg(profileImg)
//                    .thumbnailImg(thumbnailImageUrl)
//                    .role("ROLE_USER")
//                    .build();
//
//            userRepository.save(user);
//
//            UserDTO userDTO = UserDTO.builder()
//                    .username(username)
//                    .name(kakaoResponse.getName())
//                    .profileImg(profileImg)
//                    .role("ROLE_USER")
//                    .build();
//
//            return new CustomOAuth2User(userDTO);
//
//        } else {
//            // 사용자가 존재할 경우 조회된 데이터로 반환
//            existUser = existUser.toBuilder()
//                    .name(kakaoResponse.getName())
//                    .profileImg(String.valueOf(kakaoResponse.getProfile().get("profile_image_url")))
//                    .build();
//
//            log.info("--------------------------user existed !!!!!!!----------------------");
//            userRepository.save(existUser);
//
//            UserDTO userDTO = UserDTO.builder()
//                    .username(existUser.getUsername())
//                    .name(existUser.getName())
//                    .profileImg(existUser.getProfileImg())
//                    .role(existUser.getRole())
//                    .build();
//
//            return new CustomOAuth2User(userDTO);
//        }
//    }
//}
