//package com.bside.backendapi.global.oauth.application;
//
//import com.bside.backendapi.domain.member.domain.Member;
//import com.bside.backendapi.domain.member.repository.MemberRepository;
//import com.bside.backendapi.domain.member.vo.LoginId;
//import com.bside.backendapi.domain.member.vo.SocialType;
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
//import java.util.Optional;
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
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//
//        SocialType socialType = getSocialType(registrationId);
//
//        String userNameAttributeName = userRequest.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // 유저 정보를 통해 OAuthAttributes 객체 생성
//        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, userNameAttributeName, attributes);
//
//        // OAuthLoginId : kakao_123457897
//        String oAuth2LoginId = socialType.name() + "_" + attributes.get(userNameAttributeName);
//
//        log.info("Custom OAuth2 User Service : {}", oAuth2LoginId);
//
//        Member member = loadOrSaveUser(oAuth2LoginId, oAuth2Attributes);
//
//        return new CustomOAuth2User(member, oAuth2Attributes);
//    }
//
//    private Member loadOrSaveUser(final String oAuth2LoginId, final OAuth2Attributes oAuth2Attributes) {
//        Optional<Member> loadUser = memberRepository.findByLoginId(LoginId.from(oAuth2LoginId));
//
//        return loadUser.orElseGet(() ->
//                memberRepository.save(oAuth2Attributes.toEntity(oAuth2LoginId, oAuth2Attributes.getOAuth2UserInfo())));
//    }
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
