package com.bside.backendapi.domain.user.controller;

import com.bside.backendapi.domain.user.dto.TokenDto;
import com.bside.backendapi.domain.user.dto.TokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @PostMapping("/")
    public ResponseEntity<String> authorize(@RequestBody TokenRequest tokenRequest) {
//        return ResponseEntity.ok(authService.accessToken(tokenRequest));
        return ResponseEntity.ok("testtoken");
    }
}
