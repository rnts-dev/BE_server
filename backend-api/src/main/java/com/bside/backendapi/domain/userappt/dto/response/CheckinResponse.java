package com.bside.backendapi.domain.userappt.dto.response;

import com.bside.backendapi.domain.userappt.entity.ArriveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
@AllArgsConstructor
public class CheckinResponse {

    private boolean isLate;
    private boolean isRegistPenalty;
    private Long resTime;
    private boolean isAlreadyCheckedIn;
//    private ArriveType arriveType;

}
