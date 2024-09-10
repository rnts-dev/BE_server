package com.bside.backendapi.domain.appointment.application;

import com.bside.backendapi.domain.appointmentMember.domain.entity.AppointmentMember;
import com.bside.backendapi.domain.appointmentMember.domain.repository.AppointmentMemberRepository;
import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.util.GivenMember;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/test-data.sql") // SQL 스크립트 실행
public class AppointmentViewServiceTest {

    @Autowired MockMvc mockMvc;
    @Autowired private AppointmentMemberRepository appointmentMemberRepository;

    static Member member = GivenMember.toEntity();

    @BeforeEach
    void setUp() {
        // mock data 로 유저 생성
        CustomOAuth2User mockUser = new CustomOAuth2User(member);

        // 인증 토큰 생성 후 SecurityContext에 설정
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // DB에 필요한 기본 데이터 저장
//        appointmentRepository.save(appointment);
//        appointmentMemberRepository.save(appointmentMember);
    }

    @Test
    public void test_NPlusOne() {
        List<AppointmentMember> list = appointmentMemberRepository.findAllByMemberId(1L);

        for(AppointmentMember am : list) {
            System.out.println("appointment_title : " + am.getAppointment().getTitle().title());
        }
    }

    @Test
    public void fix_NPlusOne() {
        List<AppointmentMember> list = appointmentMemberRepository.findAllWithAppointmentsByMemberId(1L);

        for(AppointmentMember am : list) {
            System.out.println("appointment_title : " + am.getAppointment().getTitle().title());
        }
    }

}
