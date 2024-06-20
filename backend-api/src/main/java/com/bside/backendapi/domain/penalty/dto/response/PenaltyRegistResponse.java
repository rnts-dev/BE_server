package com.bside.backendapi.domain.penalty.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PenaltyRegistResponse {
    private Long penaltyId;
    private Long receiverId;
}
