package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.vo.LoginId;
import lombok.Data;

@Data
public class LoginIdRequest {

    private LoginId loginId;
}