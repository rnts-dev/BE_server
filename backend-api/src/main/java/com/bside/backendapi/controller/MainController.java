//package com.bside.backendapi.controller;
//
//import com.bside.backendapi.domain.user.entity.KakaoUserInfo;
//import com.bside.backendapi.global.jwt.util.JwtUtil;
//import com.bside.backendapi.global.oauth.service.CustomOAuth2UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stekage com.bside.backendapi.controller;
////
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.ResponseBody;
////
////@Controller
////public class MyController {
////
////    @GetMapping("/my")
////    @ResponseBody
////    public String myAPI() {
////        return "my route";
////    }
////}reotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@AllArgsConstructor
//public class MainController {
//
//    private CustomOAuth2UserService customOAuth2UserService;
//    private JwtUtil jwtUtil;
//
//    @GetMapping("/")
//    @ResponseBody
//    public String mainAPI() {
//        return "main route";
//    }
//
//    @RequestMapping("/kakao-redirect")
//    public String redirectUrl(@RequestParam String code) throws Exception {
//        System.out.println("code = " + code);
//        return "redirect:/kakao";
//    }
//
//    @GetMapping("/user")
//    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//
//        return principal.getAttributes();
//    }
//
//    @GetMapping("/oauth2/authorization/")
//    public String startOAuth2AuthorizationFlow() {
//        return "redirect:/login/oauth2/code/kakao";
//    }
//
//
//    // OAuth2 콜백 엔드포인트
//    @GetMapping("/login/oauth2/code/{provider}")
//    public String handleCallback(OAuth2UserRequest principal) {
//        // OAuth2 토큰을 교환하고 사용자 정보를 가져오는 로직을 호출
//        customOAuth2UserService.loadUser(principal);
//
//        // 사용자가 로그인 완료 후에 표시될 페이지로 리다이렉션
//        return "redirect:/oauth_callback";
//    }
//
//
//}
