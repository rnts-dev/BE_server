package com.bside.backendapi.domain.userappt.dto.request;


import java.time.LocalTime;

public class CheckInRequest {

    private boolean isLate;
    private LocalTime arriveTime;

    private int checkCode;
}
