package com.bside.backendapi.domain.penalty.dto;

import com.bside.backendapi.domain.appointment.dto.AppointmentDTO;
import com.bside.backendapi.domain.penalty.entity.Penalty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyDTO {

    private Long id;
    private String content;
    private String penaltyType;
    private int fine;
    private Long userId; // 필요 시 사용자 ID만 포함

    // 이 필드를 직렬화에서 제외하거나, 필요한 경우에만 포함
    private AppointmentDTO appointment;

    public static PenaltyDTO toDTO(Penalty penalty) {
        AppointmentDTO appointmentDTO = new AppointmentDTO(penalty.getAppointment().getId(), penalty.getAppointment().getTitle(), penalty.getAppointment().getCount(), penalty.getAppointment().getTime(), penalty.getAppointment().getPlace(), penalty.getAppointment().getLatitude(), penalty.getAppointment().getLongitude());
        return new PenaltyDTO(
                penalty.getId(),
                penalty.getContent(),
                penalty.getPenaltyType().name(),
                penalty.getFine(),
                penalty.getUser().getId(),
                appointmentDTO
        );
    }
}
