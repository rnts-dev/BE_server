package com.bside.backendapi.global.oauth.application;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.Name;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.domain.vo.RoleType;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import com.bside.backendapi.global.oauth.domain.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    // 리소스 서버로부터 받은 userRequest 데이터에 대한 후처리하는 함수
    // 메서드 종료시 @AuthenticationPrincipal 이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        KakaoResponse kakaoResponse = new KakaoResponse(attributes);

        String provider = kakaoResponse.getProvider();
        String providerId = kakaoResponse.getProviderId();
        String name = provider + "_" + providerId;
        String profileUrl = kakaoResponse.getProfile().get("profile_image_url").toString();
        Nickname nickname = kakaoResponse.getName();
        Email email = kakaoResponse.getEmail();

        // 입력된 정보에 맞는 회원정보가 있는지 확인
        Member member = memberRepository.findByName(name).orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 존재하지 않는다면 회원정보를 저장하고 CustomOAuth2User 반환
        if (member == null) {
            // 회원가입 + DB 추가
            member = Member.builder()
                    .name(Name.from(name))
                    .nickname(nickname)
                    .email(email)
                    .profileUrl(profileUrl)
                    .role(RoleType.USER)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            memberRepository.save(member);
        }

        return new CustomOAuth2User(member, attributes);
    }

}
