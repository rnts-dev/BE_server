package com.bside.backendapi.domain.appointment.domain;

import com.bside.backendapi.domain.appointment.domain.Appointment;
import com.bside.backendapi.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "appointment_member")
public class AppointmentMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_member_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public AppointmentMember(Long id, Appointment appointment, Member member) {
        this.id = id;
        this.appointment = appointment;
        this.member = member;
    }

}
