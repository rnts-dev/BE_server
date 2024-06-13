package com.bside.backendapi.domain.penalty.domain.persist;

import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
import com.bside.backendapi.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "penalty_type", nullable = false)
    private PenaltyType penaltyType;

    private String content;

    private int fine;

    private Long penaltyCreatorId;

    // 엔티티가 아닌 값 타입, 임베디드 타에 대해 1:N 매핑으로 테이블 생성 후 데이터 저장
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Long> penaltyReceivedMemberId;

    @Builder
    public Penalty(Long id, PenaltyType penaltyType, String content, int fine,
                   Long penaltyCreatorId, List<Long> penaltyReceivedMemberId) {
        this.id = id;
        this.penaltyType = penaltyType;
        this.content = content;
        this.fine = fine;
        this.penaltyCreatorId = penaltyCreatorId;
        this.penaltyReceivedMemberId = penaltyReceivedMemberId;
    }

    // 비즈니스 로직 추가

}
