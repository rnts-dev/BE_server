package com.bside.backendapi.domain.penalty.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceivedPenaltyRepository extends JpaRepository<ReceivedPenalty, Long> {
    List<Penalty> findByMemberId(Long memberId);
}
