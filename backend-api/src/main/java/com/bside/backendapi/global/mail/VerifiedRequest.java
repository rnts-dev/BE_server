package com.bside.backendapi.global.mail;

import lombok.Data;

@Data
public class VerifiedRequest {

    private String mail;
    private String authCode;
}
