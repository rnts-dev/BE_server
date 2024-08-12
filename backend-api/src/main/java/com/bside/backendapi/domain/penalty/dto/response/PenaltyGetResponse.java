package com.bside.backendapi.domain.penalty.dto.response;

import com.bside.backendapi.domain.penalty.domain.persist.Penalty;
import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PenaltyGetResponse {
    private Long penaltyId;

    private PenaltyType penaltyType;

    private String content;

    private Long penaltyCreatorId;


    public static PenaltyGetResponse empty() {
        return new PenaltyGetResponse(null, null, null,  null);
    }

    public static PenaltyGetResponse of(final Penalty penalty){
        return new PenaltyGetResponse(
                penalty.getId(),
                penalty.getPenaltyType(),
                penalty.getContent(),
                penalty.getPenaltyCreatorId()
        );
    }

}
