package com.bside.backendapi.domain.appointmentMember.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinRequest {
    private Long appointmentId;
}
