package com.bid.auction.domain.refund.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RefundResponse {
	@Getter
	@AllArgsConstructor
	@Builder
	public static class PgRefundResponse {
		private String pgUid;
		private String merchantUid;
		private Integer amount;
		private String reason;
		private LocalDateTime refundedAt;
		private LocalDateTime refundRequestedAt;
	}

	@Getter
	@AllArgsConstructor
	@Builder
	public static class RefundSuccessResponse {
		private Long refundId;
		private String pgUid;
		private String merchantUid;
		private Integer amount;
		private LocalDateTime refundedAt;
	}
}
