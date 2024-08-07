package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.vo.LoginId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchIdResponse implements Serializable {

    private LoginId loginId;

    public static SearchIdResponse of(final LoginId loginId) {
        return new SearchIdResponse(loginId);
    }

}
