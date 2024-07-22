package com.bside.backendapi.domain.member.dto;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.vo.Tendency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TendencyRequest {

    private Tendency tendency;

    public static TendencyRequest of(final Tendency tendency) {
        return new TendencyRequest(tendency);
    }

    public Member toEntity() {
        return Member.builder().tendency(tendency).build();
    }
}
