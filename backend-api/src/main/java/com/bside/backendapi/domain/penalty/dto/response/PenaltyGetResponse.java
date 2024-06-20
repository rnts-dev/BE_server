package com.bside.backendapi.domain.penalty.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PenaltyGetResponse {
    private Long penaltyId;
    private boolean found;
}
