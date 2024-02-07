package com.bid.auction.domain.refund.entity;

import java.time.LocalDateTime;

import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.payment.enums.PaymentMethod;
import com.bid.auction.domain.refund.enums.RefundStatus;
import com.bid.auction.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refund_request_id")
	private Long id;
	@Column(nullable = false)
	private Integer amount;
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethod refundMethod;
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private RefundStatus status;

	@Column(nullable = false)
	private String reason;

	@Column(nullable = false)

	private LocalDateTime requestedAt;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User requestedUser;

	@ManyToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;

	@Builder
	public RefundRequest(
		Integer amount,
		PaymentMethod refundMethod,
		RefundStatus status,
		String reason,
		LocalDateTime requestedAt,
		User requestedUser,
		Payment payment
	) {
		this.amount = amount;
		this.refundMethod = refundMethod;
		this.status = status;
		this.reason = reason;
		this.requestedAt = requestedAt;
		this.requestedUser = requestedUser;
		this.payment = payment;
	}

	public void complete() {
		this.status = RefundStatus.REFUND;
	}
}
