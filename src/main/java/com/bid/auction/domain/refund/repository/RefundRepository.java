package com.bid.auction.domain.refund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bid.auction.domain.refund.entity.Refund;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
}
