package com.bside.backendapi.domain.penalty.repository;

import com.bside.backendapi.domain.penalty.entity.Penalty;
import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import com.bside.backendapi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    Penalty findPenaltyByUser(User user);

    Penalty findPenaltyByAppointmentId(Long appointmentId);

    List<Penalty> findAllByUserId(Long userId);

}
