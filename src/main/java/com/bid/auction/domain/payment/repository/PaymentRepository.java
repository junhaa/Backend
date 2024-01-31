package com.bid.auction.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid.auction.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
