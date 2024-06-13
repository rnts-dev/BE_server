package com.bside.backendapi.domain.penalty.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserApptPenaltyResponse {

    private String content;
    private int lateTime;
    private String place;
    private LocalDateTime time;

}
