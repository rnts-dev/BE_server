//package com.bside.backendapi.global.oauth.application;
//
//import com.bside.backendapi.domain.member.domain.Member;
//import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
//import com.bside.backendapi.domain.member.domain.vo.LoginId;
//import com.bside.backendapi.domain.member.domain.vo.SocialType;
//import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
//import com.bside.backendapi.global.oauth.domain.OAuth2Attributes;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Map;
//
//@Slf4j
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // kakao
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        SocialType socialType = getSocialType(registrationId);
//
//        // userNameAttributeName : socialId를 불러올 키 (카카오 : id)
//        String userNameAttributeName = userRequest.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // 유저 정보를 통해 OAuthAttributes 객체 생성
//        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, userNameAttributeName, attributes);
//
//        // OAuthLoginId : kakao_123457897
//        String OAuthLoginId = socialType.name() + "_" + attributes.get(userNameAttributeName);
//
//        Member member = loadOrSaveMember(OAuthLoginId, oAuth2Attributes);
//
//        return new CustomOAuth2User(member, oAuth2Attributes);
//    }
//
//    private Member loadOrSaveMember(String OAuthLoginId, OAuth2Attributes oAuth2Attributes) {
//        Member member = memberRepository.findByLoginId(LoginId.from(OAuthLoginId)).orElse(null);
//        log.info("loadOrSaveMember 호출 --------------");
//
//        if (member == null) {
//            return memberRepository.save(oAuth2Attributes.toEntity(OAuthLoginId, oAuth2Attributes.getOauth2UserInfo()));
//        }
//        return member;
//    }
//
//
//
//
//    public Member processOAuth2User(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        SocialType socialType = getSocialType(registrationId);
//
//        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, userNameAttributeName, attributes);
//        String OAuthLoginId = socialType.name() + "_" + attributes.get(userNameAttributeName);
//
//        return loadOrSaveMember(OAuthLoginId, oAuth2Attributes);
//    }
//
//
//
//
//
//    private SocialType getSocialType(String registrationId) {
//        if("kakao".equals(registrationId)) {
//            return SocialType.KAKAO;
//        }
//        if("naver".equals(registrationId)) {
//            return SocialType.NAVER;
//        }
//        return SocialType.GOOGLE;
//    }
//}
