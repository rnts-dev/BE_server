package com.bside.backendapi.domain.appointment.api;

import com.bside.backendapi.domain.appointment.application.AppointmentService;
import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.appointment.domain.CustomAppointmentType;
import com.bside.backendapi.domain.appointment.dto.AppointmentRequest;
import com.bside.backendapi.domain.appointment.dto.AppointmentResponse;
import com.bside.backendapi.domain.appointment.util.GivenAppointment;
import com.bside.backendapi.domain.appointment.util.GivenCustomType;
import com.bside.backendapi.domain.appointment.vo.Title;
import com.bside.backendapi.domain.member.domain.WithAuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class AppointmentControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean AppointmentService appointmentService;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("요청 받은 약속 정보를 저장한다.")
    @WithAuthUser
    void 약속_생성() throws Exception {
        // given
        AppointmentRequest request = AppointmentRequest.of(GivenAppointment.toEntity());

        String body = objectMapper.writeValueAsString(request);

        // when, then
        when(appointmentService.create(any(), any())).thenReturn(AppointmentResponse.of(GivenAppointment.toEntity()));

        mockMvc.perform(post("/api/v1/appointment")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("약속을 수정한다.")
    @WithAuthUser
    void 약속_수정() throws Exception {
        // given
        Appointment updateAppointment = Appointment.builder()
                .title(Title.from("타이틀변경"))
                .customAppointmentTypeId(1L)
                .build();

        String body = objectMapper.writeValueAsString(updateAppointment);

        // when, then
        doNothing().when(appointmentService).udpate(any(Appointment.class), anyLong());

        mockMvc.perform(patch("/api/v1/appointment/{appointmentId}", 1L)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
