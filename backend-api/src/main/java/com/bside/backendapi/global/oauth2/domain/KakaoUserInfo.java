package com.bside.backendapi.global.oauth2.domain;

public class KakaoUserInfo {
    private final String kakaoId;
    private final String email;
    private final String nickname;

    public KakaoUserInfo(String kakaoId, String email, String nickname) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.nickname = nickname;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
