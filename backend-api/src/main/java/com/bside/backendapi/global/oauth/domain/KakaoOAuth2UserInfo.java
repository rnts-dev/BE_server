package com.bside.backendapi.global.oauth.domain;

import java.util.Map;

@SuppressWarnings("unchecked")
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private final String socialId = String.valueOf(attributes.get("id"));
    private final Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
    private final Map<String, Object> profile = (Map<String, Object>) account.get("profile");

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return socialId;
    }

    @Override
    public String getEmail() {
        return (String) account.get("email");
    }

    @Override
    public String getNickname() {
        return (String) profile.get("nickname");
    }

    @Override
    public String getProfileUrl() {
        return (String) profile.get("thumbnail_image_url");
    }
}
