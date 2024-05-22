package com.bside.backendapi.global.oauth.service;

import com.bside.backendapi.domain.user.dto.UserDTO;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.user.repository.UserRepository;
import com.bside.backendapi.global.oauth.dto.CustomOAuth2User;
import com.bside.backendapi.global.oauth.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("oAuth2User : {}", oAuth2User);
        log.info("attributes : {}", attributes);

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        KakaoResponse kakaoResponse = new KakaoResponse(attributes);

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
//        String username = kakaoResponse.getProvider() + " " + kakaoResponse.getProviderId();
        String username = "kakao " + kakaoResponse.getSocialId();
        User existUser = userRepository.findByUsername(username);

        // 프로필 이미지, 썸네일 이미지
        Map<String, Object> profile = kakaoResponse.getProfile();
        String profileImg = String.valueOf(profile.get("profile_image"));
        String thumbnailImg = String.valueOf(profile.get("thumbnail_image_url"));

        if (existUser == null) {
            // 사용자가 없다면 생성해서 DB 저장
            User user = User.builder()
                    .username(username)
//                    .email(kakaoResponse.getEmail())
                    .name(kakaoResponse.getName())
                    .role("ROLE_USER")
                    .profileImg(profileImg)
                    .thumbnailImg(thumbnailImg)
                    .build();

            userRepository.save(user);

            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .name(kakaoResponse.getName())
                    .role("ROLE_USER")
                    .build();

            return new CustomOAuth2User(userDTO);
        } else {
            // 사용자가 존재할 경우
            existUser = User.builder()
//                    .email(kakaoResponse.getEmail())
                    .name(kakaoResponse.getName())
                    .build();

            userRepository.save(existUser);

            UserDTO userDTO = UserDTO.builder()
                    .username(existUser.getUsername())
                    .name(kakaoResponse.getName())
                    .role(existUser.getRole())
                    .build();

            return new CustomOAuth2User(userDTO);
        }
    }
}
