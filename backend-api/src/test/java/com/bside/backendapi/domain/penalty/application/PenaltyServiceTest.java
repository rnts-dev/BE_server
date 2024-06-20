package com.bside.backendapi.domain.penalty.application;

import com.bside.backendapi.domain.appointment.domain.persist.Appointment;
import com.bside.backendapi.domain.appointment.domain.persist.AppointmentRepository;
import com.bside.backendapi.domain.penalty.domain.persist.PenaltyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PenaltyServiceTest {

    @Mock
    private PenaltyRepository penaltyRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private PenaltyService penaltyService;

    private Appointment mockAppointment;

    @BeforeEach
    void setUp() {
        // Mock Appointment 객체 생성
        mockAppointment = new Appointment(1L,);
        mockAppointment.setId(1L);
        mockAppointment.setPenaltyId(null); // 테스트 시 penaltyId를 null로 설정
    }

    @Test
    public void testFindByAppointment_Success() {


        // Mock Repository 설정
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        // 테스트: appointmentId로 패널티 ID 조회
        Long penaltyId = penaltyService.findByAppointment(1L);

        // 결과 검증
        assertEquals(1L, penaltyId);
    }

}
