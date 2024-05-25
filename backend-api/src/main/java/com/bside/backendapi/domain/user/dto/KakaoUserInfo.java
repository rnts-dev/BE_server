package com.bside.backendapi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class KakaoUserInfo {
    private Long kakaoId;
    private String nickname;
    private String profileImageUrl;
}
