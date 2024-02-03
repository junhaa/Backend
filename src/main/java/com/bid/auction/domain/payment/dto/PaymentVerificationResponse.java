package com.bid.auction.domain.payment.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record PaymentVerificationResponse(
	String pgPaymentOrderUid,
	String merchantPaymentOrderUid,
	Integer paymentOrderAmount,
	LocalDateTime requestedAt
) {
}
