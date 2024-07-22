package com.bside.backendapi.domain.penalty.domain.persist;

import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceivedPenalty extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "received_penalty_id")
    private Long id;

    private Long memberId;
    private Long penaltyId;

    @Builder
    public ReceivedPenalty(Long memberId, Long penaltyId){
        memberId = this.memberId;
        penaltyId = this.penaltyId;
    }

    //비지니스 로직 추가



}
