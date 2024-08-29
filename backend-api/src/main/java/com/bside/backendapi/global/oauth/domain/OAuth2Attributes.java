package com.bside.backendapi.global.oauth.domain;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class OAuth2Attributes {

    private final String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private final OAuth2UserInfo oAuth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    public static OAuth2Attributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
        if (Objects.equals(socialType, SocialType.KAKAO)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        // 추후 구글, 네이버 추가
        return null;
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(String oAuthLoginId, OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .loginId(LoginId.from(oAuthLoginId))
                .mail(Mail.from(oAuth2UserInfo.getMail()))
                .password(Password.from(UUID.randomUUID().toString())) // 임시 비밀번호 (소셜 로그인은 비밀번호가 없음)
                .nickname(Nickname.from(oAuth2UserInfo.getNickname()))
                .profileImage(oAuth2UserInfo.getProfileImage())
                .role(RoleType.USER)
                .socialType(SocialType.KAKAO)
                .socialId(oAuth2UserInfo.getSocialId())
                .build();
    }
}