package com.bside.backendapi.global.oauth.domain;

import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KakaoResponse {

    public static String providerId;
    public static Map<String, Object> account;
    public static Map<String, Object> profile;

    public KakaoResponse(Map<String, Object> attributes) {
        providerId = String.valueOf(attributes.get("id"));
        account = (Map<String, Object>) attributes.get("kakao_account");
        profile = (Map<String, Object>) account.get("profile");
    }

    public String getProvider() {
        return "kakao";
    }

    public String getProviderId() {
        return providerId;
    }

    public Email getEmail() {
        return Email.from(account.get("email").toString());
    }

    public Nickname getName() {
        return Nickname.from(profile.get("nickname").toString());
    }

    public Map<String, Object> getProfile() {
        return profile;
    }
}