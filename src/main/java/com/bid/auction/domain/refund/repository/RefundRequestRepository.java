package com.bid.auction.domain.refund.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.refund.entity.RefundRequest;

@Repository
public interface RefundRequestRepository extends JpaRepository<RefundRequest, Long> {
	Optional<RefundRequest> findByPayment(Payment payment);
}
