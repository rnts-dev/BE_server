package com.bside.backendapi.global.oauth.domain;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public abstract class OAuth2UserInfo {

    // 추상클래스를 상속받는 클래스에서만 사용할 수 있도록 protected 사용
    protected final Map<String, Object> attributes;

    public abstract String getSocialId(); // socialId
    public abstract String getMail();
    public abstract String getNickname();
    public abstract String getProfileImage();
}
