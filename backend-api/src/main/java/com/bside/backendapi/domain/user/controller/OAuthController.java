package com.bside.backendapi.domain.user.controller;

import com.bside.backendapi.domain.user.dto.TokenRequest;
import com.bside.backendapi.domain.user.service.OAuth2UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class OAuthController {

    private OAuth2UserService auth2UserService;

    @PostMapping("/")
    public ResponseEntity<String> authorize(@RequestBody TokenRequest tokenRequest) {
//        return ResponseEntity.ok(authService.accessToken(tokenRequest));
        return ResponseEntity.ok("testtoken");
    }

    @PostMapping("/oauth2/callback")
    public ResponseEntity<?> handleOAuth2Callback(@RequestBody Map<String, String> body) {
        String code = body.get("code");

        return ResponseEntity.ok().build();
    }
}
