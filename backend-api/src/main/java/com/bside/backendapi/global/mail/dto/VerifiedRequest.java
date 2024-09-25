package com.bside.backendapi.global.mail.dto;

import lombok.Data;

@Data
public class VerifiedRequest {

    private String mail;
    private String authCode;
}
