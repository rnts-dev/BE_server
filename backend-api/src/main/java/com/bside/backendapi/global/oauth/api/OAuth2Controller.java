package com.bside.backendapi.global.oauth.api;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.global.oauth.application.OAuth2Service;
import com.bside.backendapi.global.oauth.domain.KakaoUserInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Member", description = "Member API Document")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;


    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {

        String accessToken = oAuth2Service.getAccessTokenFromKakao(code);
        KakaoUserInfo kakaoUserInfo = oAuth2Service.getKakaoUserInfo(accessToken);
        // 3. 사용자 정보를 이용해 회원을 생성하거나 조회
        Member member = oAuth2Service.loadOrSaveMember(kakaoUserInfo);

        return ResponseEntity.ok("Access Token: " + accessToken);
    }






}
