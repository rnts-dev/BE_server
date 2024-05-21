package com.bside.backendapi.domain.userappt.dto;

import com.bside.backendapi.domain.userappt.entity.PenaltyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserApptResponse {

    private Long id;

    private String apTitle;
    private String apPlace;
    private LocalDateTime apTime;

}
