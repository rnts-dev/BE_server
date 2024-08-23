package com.bside.backendapi.domain.penalty.dto.response;

import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PenaltyResponse{

    private Long penaltyId;
    private boolean result;

    public static PenaltyResponse of(final Long penaltyId, final boolean result){
        return new PenaltyResponse(
                penaltyId,
                result
        );
    }

}
