package com.bside.backendapi.global.oauth.application;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.repository.MemberRepository;
import com.bside.backendapi.domain.member.vo.*;
import com.bside.backendapi.global.oauth.domain.KakaoUserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2Service {

    private final MemberRepository memberRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    public String getAccessTokenFromKakao(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_secret", clientSecret);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token",
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                return extractAccessToken(root);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse access token", e);
            }
        } else {
            throw new RuntimeException("Failed to fetch access token");
        }
    }


    // 카카오 사용자 정보 가져오기
    public KakaoUserInfo getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                String kakaoId = root.path("id").asText();
                String email = root.path("kakao_account").path("email").asText();
                String nickname = root.path("properties").path("nickname").asText();

                return new KakaoUserInfo(kakaoId, email, nickname);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse user info", e);
            }
        } else {
            throw new RuntimeException("Failed to fetch user info");
        }
    }


    // 카카오 사용자 정보로 회원 생성 또는 조회
    public Member loadOrSaveMember(KakaoUserInfo kakaoUserInfo) {

        //id string으로
        String kakaoId = "kakao_" + kakaoUserInfo.getKakaoId();



        // DB에서 사용자 조회
        Member member = memberRepository.findBySocialIdAndSocialType(kakaoId, SocialType.KAKAO).orElse(null);



        if (member == null) {
            // 첫 로그인 - 새로운 회원 객체 생성
            member = Member.builder()
                    .loginId(LoginId.from(kakaoId))
                    .mail(Mail.from(kakaoUserInfo.getEmail()))
                    .password(Password.from("kakao"))
                    .nickname(Nickname.from(kakaoUserInfo.getNickname()))
                    .profileImage(null) // 처음 로그인 시 프로필 URL은 null
                    .tendency(null) // 초기 성향은 null
                    .role(RoleType.USER) // 기본 역할 설정 (예: USER)
                    .socialType(SocialType.KAKAO)
                    .socialId(kakaoId)
                    .agreeTerms(null) // 동의사항은 초기에는 null
                    .build();
            // DB에 새로운 회원 저장
            memberRepository.save(member);
        }

        return member;
    }

    private String extractAccessToken(JsonNode root) {
        return root.path("access_token").asText();
    }
}

