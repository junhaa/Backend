package com.bid.auction.domain.refund.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refund_id")
	private Long id;

	@Column(nullable = false)
	private Integer amount;
	@Column(nullable = false)
	private String reason;
	@Column(nullable = false)
	private LocalDateTime refundedAt;

	@Column(nullable = false)
	private LocalDateTime refundRequestedAt;

	@OneToOne
	@JoinColumn(name = "refund_request_id", referencedColumnName = "refund_request_id")
	private RefundRequest request;

	@Builder
	public Refund(
		Integer amount,
		String reason,
		LocalDateTime refundedAt,
		LocalDateTime refundRequestedAt,
		RefundRequest request
	) {
		this.amount = amount;
		this.reason = reason;
		this.refundedAt = refundedAt;
		this.refundRequestedAt = refundRequestedAt;
		this.request = request;
	}
}
