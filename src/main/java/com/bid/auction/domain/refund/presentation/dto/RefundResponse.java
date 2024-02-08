package com.bid.auction.domain.refund.presentation.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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
		@Schema(
			type = "int",
			example = "1",
			description = "환불 데이터의 PK"
		)
		private Long refundId;
		@Schema(
			type = "string",
			example = "imp_245402750188",
			description = "처리 완료된 결제 PG사 고유번호"
		)
		private String pgUid;
		@Schema(
			type = "string",
			example = "1_productOrder_uuid18",
			description = "처리 완료된 결제 서비스 고유번호"
		)
		private String merchantUid;
		@Schema(
			type = "int",
			example = "1000",
			description = "환불 처리 완료 금액"
		)
		private Integer amount;
		@Schema(
			type = "string",
			example = "2024-02-01T15:30:00",
			description = "환불 완료 일시"
		)
		private LocalDateTime refundedAt;
	}
}
