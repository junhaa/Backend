package com.bid.auction.domain.payment.dto;

import java.time.LocalDateTime;

import com.bid.auction.domain.payment.enums.PaymentMethod;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PaymentResponse {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	@Builder
	public static class PaymentCompleteResponse {
		private Long paymentId;
		private Integer paymentAmount;
		private PaymentMethod paymentMethod;
		private LocalDateTime requestedAt;
		private LocalDateTime approvedAt;
		private String pgPaymentOrderUid;
		private String merchantPaymentOrderUid;
	}
}
