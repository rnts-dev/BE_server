package com.bside.backendapi.domain.member.api;

import com.bside.backendapi.domain.member.dto.KakaoUserInfo;
import com.bside.backendapi.domain.member.entity.User;
import com.bside.backendapi.domain.member.repository.UserRepository;
import com.bside.backendapi.domain.member.application.KaKaoService;
import com.bside.backendapi.domain.member.application.UserService;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final KaKaoService kaKaoService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @GetMapping("/token/{userId}")
    public String login(@PathVariable Long userId){

        return userService.creatUserToken(userId);
    }

    @PostMapping("/kakao/login")
    public ResponseEntity<String> kakaologin(@RequestParam String code){
        String accessToken = kaKaoService.getAccessTokenFromKakao(code);
        KakaoUserInfo kakaoUserInfo = kaKaoService.getUserInfo(accessToken);

        User user = userRepository.findByKakaoId(kakaoUserInfo.getKakaoId())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setKakaoId(kakaoUserInfo.getKakaoId());
                    newUser.setNickName(kakaoUserInfo.getNickname());
                    newUser.setProfileImg(kakaoUserInfo.getProfileImageUrl());
                    newUser.setRole("USER"); // 사용자의 기본 역할 설정
                    return userRepository.save(newUser);
                });

        String token = jwtUtil.createJwt(String.valueOf(user.getId()), user.getRole(), 3600000L);

        return ResponseEntity.ok(token);
    }


//    @GetMapping("/login")
//    public String login(@RequestBody Map<String, String> request) {
//        String code = request.get("code");
//
//        //post방식으로 key=value 데이터를 요청(카카오한테)
//        RestTemplate rt = new RestTemplate(); // 라이브러리
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "Authorization");
//        params.add("client_id", clientId);
//        params.add("redirect_uri", redirectUri);
//        params.add("code", code);
//
//        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        //Http 요청하기 - Post방식으로 그리고 response 변수의 응답받음.
//        ResponseEntity<Map> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                Map.class
//        );
//
//        // 카카오 서버로부터 받은 액세스 토큰
//        String accessToken = (String) response.getBody().get("access_token");
//
//        System.out.println(accessToken + "=====================");
//
//        // 액세스 토큰을 사용하여 사용자 정보 요청
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.add("Authorization", "Bearer " + accessToken);
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
//
//        ResponseEntity<Map> profileResponse = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.GET,
//                kakaoProfileRequest,
//                Map.class
//        );
//
//        // 사용자 정보 가져오기
//        Map<String, Object> profile = (Map<String, Object>) profileResponse.getBody().get("kakao_account");
//        String nickname = (String) ((Map<String, Object>) profile.get("profile")).get("nickname");
//
//        // 사용자 정보를 DB에 저장
//        User user = User.builder()
//                .name(nickname)
//                .build();
//
//        userRepository.save(user);
//
//        // JWT 토큰 생성
//        String jwtToken = jwtUtil.createJwt(nickname, "USER_ROLE", 2592000L);
//
//        return jwtToken;
//    }

    @GetMapping("/create-temp-user")
    public ResponseEntity<User> createTemporaryUser() {
        User tempUser = userService.createTemporaryUser();
        return ResponseEntity.ok(tempUser);
    }

    @PostMapping("/tendency")
    public ResponseEntity<Object> getTendency(@RequestBody String tendency, HttpServletRequest httpRequest) {
        userService.getTendency(tendency, httpRequest);
        return ResponseEntity.ok().build();
    }

}
