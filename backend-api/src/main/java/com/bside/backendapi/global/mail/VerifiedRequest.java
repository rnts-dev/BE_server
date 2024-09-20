package com.bside.backendapi.global.mail;

import com.bside.backendapi.domain.member.vo.Mail;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VerifiedRequest {

    @Valid
    private Mail mail;
    private String authCode;
}
