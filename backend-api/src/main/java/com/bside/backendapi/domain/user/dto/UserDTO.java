package com.bside.backendapi.domain.user.dto;

import com.bside.backendapi.domain.penalty.dto.PenaltyDTO;
import com.bside.backendapi.domain.penalty.dto.ReceivedPenaltyDTO;
import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import com.bside.backendapi.domain.user.entity.User;
import com.bside.backendapi.domain.userappt.dto.UserApptDTO;
import com.bside.backendapi.domain.userappt.entity.UserAppt;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String profileImg;
    private String thumbnailImg;
    private String role;
    private String tendency;

    private List<UserApptDTO> userAppts;
    private List<PenaltyDTO> penalties;
    private List<ReceivedPenaltyDTO> receivedPenalties;


    public static UserDTO toDTO(User user) {
        List<UserApptDTO> userApptDTOs = user.getUserAppts().stream()
                .map(UserApptDTO::toDTO)
                .collect(Collectors.toList());

        List<PenaltyDTO> penaltyDTOs = user.getPenalties().stream()
                .map(PenaltyDTO::toDTO)
                .collect(Collectors.toList());

        List<ReceivedPenaltyDTO> receivedPenaltyDTOs = user.getReceivedPenalties().stream()
                .map(ReceivedPenaltyDTO::toDTO)
                .collect(Collectors.toList());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getProfileImg(),
                user.getThumbnailImg(),
                user.getRole(),
                user.getTendency(),  // 수정된 필드 반영
                userApptDTOs,
                penaltyDTOs,
                receivedPenaltyDTOs
        );
    }

}
