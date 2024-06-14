package com.bside.backendapi.global.oauth.domain;

public interface OAuth2Response {

    String getProvider(); // kakao
    String getProviderId(); // kakao에서 발급해주는 아이디(번호)
    String getEmail();
    String getName(); // 사용자 이름 (설정한 이름)
}
