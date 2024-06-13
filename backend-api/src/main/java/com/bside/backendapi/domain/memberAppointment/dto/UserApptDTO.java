package com.bside.backendapi.domain.memberAppointment.dto;

import com.bside.backendapi.domain.memberAppointment.entity.UserAppt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserApptDTO {

    private Long id;
    private Long userId;
    private Long appointmentId;

    public static UserApptDTO toDTO(UserAppt userAppt) {
        return new UserApptDTO(
                userAppt.getId(),
                userAppt.getUser().getId(),
                userAppt.getAppointment().getId()
        );
    }

}
