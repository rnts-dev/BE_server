package com.bside.backendapi.global.oauth.dto;

import java.util.Map;

public class KakaoResponse {

    public static String providerId;
    public static Map<String, Object> account;
    public static Map<String, Object> profile;

    public KakaoResponse(Map<String, Object> attributes) {
        providerId = String.valueOf(attributes.get("id"));
        account = (Map<String, Object>) attributes.get("kakao_account");
        profile = (Map<String, Object>) account.get("profile");
    }

    public String getProviderId() {
        return providerId;
    }

    public String getName() {
        return String.valueOf(profile.get("nickname"));
    }

    public Map<String, Object> getProfile() {
        return profile;
    }
}