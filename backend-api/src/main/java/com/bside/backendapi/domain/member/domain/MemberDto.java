package com.bside.backendapi.domain.member.domain;

import lombok.Getter;

@Getter
public class MemberDto {
    private String email;
    private String password;
    private String username;
}
