package com.bside.backendapi.domain.penalty.dto.response;

import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserApptPenaltyResponse {

    private String content;
    private int lateTime;
    private String place;
    private LocalDateTime time;

}
