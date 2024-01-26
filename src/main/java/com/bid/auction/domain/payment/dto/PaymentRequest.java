package com.bid.auction.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

public class PaymentRequest {
	@Getter
	public static class PaymentVerificationRequest {
		@NotNull
		@JsonProperty("imp_uid")
		private String impUid;
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
