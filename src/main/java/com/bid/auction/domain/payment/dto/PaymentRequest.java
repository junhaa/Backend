package com.bid.auction.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

public class PaymentRequest {
	@Getter
	public static class PaymentVerificationRequest {
		@Schema(type = "string", example = "imp_245402750188", description = "결제 요청에 PG사에서 부여한 고유번호")
		@NotNull
		@JsonProperty("imp_uid")
		private String impUid;
		@Schema(type = "string", example = "1_productOrder_uuid18",
			description = "결제 요청에 서비스에서 부여한 고유번호")
		@NotNull
		@JsonProperty("merchant_uid")
		private String merchantUid;
	}

	@Getter
	@ToString
	public static class PaymentOrderResult {

		@JsonProperty("imp_uid")
		private String pgPaymentOrderUid;
		@JsonProperty("merchant_uid")
		private String merchantPaymentOrderUid;
		@JsonProperty("status")
		private String status;
	}
}
