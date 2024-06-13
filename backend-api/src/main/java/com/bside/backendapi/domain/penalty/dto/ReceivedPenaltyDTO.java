package com.bside.backendapi.domain.penalty.dto;

import com.bside.backendapi.domain.penalty.domain.ReceivedPenalty;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedPenaltyDTO {

//    private Long id;
//    private String content;
//    private String penaltyType;
//    private int fine;
//    private Long userId; // 필요 시 사용자 ID만 포함
//
//    public ReceivedPenaltyDTO(Long id, Object content, Object name, Object fine, Long id1) {
//    }
//
//    public static ReceivedPenaltyDTO toDTO(ReceivedPenalty receivedPenalty) {
//        return new ReceivedPenaltyDTO(
//                receivedPenalty.getId(),
//                receivedPenalty.getContent(),
//                receivedPenalty.getPenaltyType(),
//                receivedPenalty.getFine(),
//                receivedPenalty.getUser().getId()
//        );
//    }

    private Long id;
    private Long penaltyId; // 연관된 Penalty의 ID
    private Long userId;    // 연관된 User의 ID
    private LocalTime resTime;
    private Object content;
    private Object penaltyType;
    private Object fine;

    public static ReceivedPenaltyDTO toDTO(ReceivedPenalty receivedPenalty) {
        ReceivedPenaltyDTO dto = new ReceivedPenaltyDTO();
        dto.setId(receivedPenalty.getId());
        dto.setPenaltyId(receivedPenalty.getPenalty().getId());
        dto.setUserId(receivedPenalty.getUser().getId());
        dto.setResTime(receivedPenalty.getResTime());
        dto.setContent(receivedPenalty.getContent());
        dto.setPenaltyType(receivedPenalty.getPenaltyType());
        dto.setFine(receivedPenalty.getFine());

        return dto;
    }
}
