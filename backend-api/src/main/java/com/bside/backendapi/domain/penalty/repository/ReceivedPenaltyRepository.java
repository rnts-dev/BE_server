package com.bside.backendapi.domain.penalty.repository;

import com.bside.backendapi.domain.penalty.entity.ReceivedPenalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceivedPenaltyRepository extends JpaRepository<ReceivedPenalty, Long> {

    List<ReceivedPenalty> findByUserId(Long userId);

}
