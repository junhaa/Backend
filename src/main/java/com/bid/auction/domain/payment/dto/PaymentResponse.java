package com.bid.auction.domain.payment.dto;

import java.time.LocalDateTime;

import com.bid.auction.domain.payment.enums.PaymentMethod;

import io.swagger.v3.oas.annotations.media.Schema;
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
		@Schema(type = "int", example = "1", description = "결제 데이터 PK")
		private Long paymentId;
		@Schema(type = "int", example = "4000", description = "결제 금액")
		private Integer paymentAmount;
		@Schema(type = "string", example = "card", description = "결제 수단")
		private PaymentMethod paymentMethod;
		@Schema(type = "string", example = "2024-02-01T15:30:00", description = "결제 요청 일시")
		private LocalDateTime requestedAt;
		@Schema(type = "string", example = "2024-02-01T15:30:00", description = "결제 승인 일시")
		private LocalDateTime approvedAt;
		@Schema(type = "string", example = "imp_245402750188",
			description = "처리 완료된 결제 PG사 고유번호")
		private String pgPaymentOrderUid;
		@Schema(type = "string", example = "1_productOrder_uuid18",
			description = "처리 완료된 결제 서비스 고유번호")
		private String merchantPaymentOrderUid;
	}
}
