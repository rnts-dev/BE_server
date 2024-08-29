package com.bside.backendapi.domain.appointment.domain;

import com.bside.backendapi.domain.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomAppointmentType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_appointment_type_id")
    private Long id;

    @ManyToOne @JsonProperty("member")
    @JoinColumn(name = "member_id")
    private Member member;

    @Length(min = 1, max = 6)
    private String typeName;

    private String imageUrl;

    @Builder
    public CustomAppointmentType(Long id, Member member, String typeName, String imageUrl) {
        this.id = id;
        this.member = member;
        this.typeName = typeName;
        this.imageUrl = imageUrl;
    }

    public void addMember(final Member member) {
        this.member = member;
    }

    public void update(final CustomAppointmentType customAppointmentType) {
        this.typeName = customAppointmentType.getTypeName();
        this.imageUrl = customAppointmentType.getImageUrl();
    }
}
