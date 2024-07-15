package com.bside.backendapi.global.oauth.application;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.RoleType;
import com.bside.backendapi.domain.member.domain.vo.SocialType;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import com.bside.backendapi.global.oauth.domain.KakaoOAuth2UserInfo;
import com.bside.backendapi.global.oauth.domain.OAuth2Attributes;
import com.bside.backendapi.global.oauth.domain.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // kakao
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);

        // userNameAttributeName : socialId (kakao : "id")
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 유저 정보를 통해 OAuthAttributes 객체 생성
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, userNameAttributeName, attributes);

        Member member = getOrSaveMember(oAuth2Attributes);

        return new CustomOAuth2User(member, oAuth2Attributes);
    }

    private Member getOrSaveMember(OAuth2Attributes oAuth2Attributes) {
        log.info("getOrSave ---------- ");
        Member member = memberRepository.findBySocialId(oAuth2Attributes.getOauth2UserInfo().getId()).orElse(null);
        if (member == null) {
            return memberRepository.save(oAuth2Attributes.toEntity(oAuth2Attributes.getOauth2UserInfo()));
        }
        return member;
    }


    private SocialType getSocialType(String registrationId) {
        if("kakao".equals(registrationId)) {
            return SocialType.KAKAO;
        }
        if("naver".equals(registrationId)) {
            return SocialType.NAVER;
        }
        return SocialType.GOOGLE;
    }
}
