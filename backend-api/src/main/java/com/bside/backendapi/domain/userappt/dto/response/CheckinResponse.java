package com.bside.backendapi.domain.userappt.dto.response;

import com.bside.backendapi.domain.userappt.entity.ArriveType;
import com.bside.backendapi.domain.userappt.entity.CheckInType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter
@AllArgsConstructor
public class CheckinResponse {

    private boolean isLate;
    private boolean isFirst;
    private Long resTime;
    private CheckInType checkInType;
//    private ArriveType arriveType;

}
