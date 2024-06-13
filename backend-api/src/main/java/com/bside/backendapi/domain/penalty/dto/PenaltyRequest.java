package com.bside.backendapi.domain.penalty.dto;

import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PenaltyRequest {

    private PenaltyType penaltyType;
    private String content;
    private int fine;

}
