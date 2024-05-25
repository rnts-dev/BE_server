package com.bside.backendapi.domain.penalty.dto;

import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import com.bside.backendapi.domain.user.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalTime;

public class ReceivedPenaltyDTO {

    private Long id;
    private String content;
    private String penaltyType;
    private int fine;
    private Long userId; // 필요 시 사용자 ID만 포함

    public ReceivedPenaltyDTO(Long id, Object content, String name, Object fine, Long id1) {
    }

    public static ReceivedPenaltyDTO toDTO(ReceivedPenalty receivedPenalty) {
        return new ReceivedPenaltyDTO(
                receivedPenalty.getId(),
                receivedPenalty.getContent(),
                receivedPenalty.getPenaltyType().name(),
                receivedPenalty.getFine(),
                receivedPenalty.getUser().getId()
        );
    }
}
