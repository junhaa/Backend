package com.bid.auction.domain.refund.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefundRequestOrder {
	@Schema(
		type = "string",
		example = "1_productOrder_uuid18",
		description = "결제 요청에 서비스에서 부여한 고유번호"
	)
	@JsonProperty("merchant_uid")
	@NotBlank
	private String merchantUid;
	@Schema(
		type = "string",
		example = "단순 변심",
		description = "환불 사유"
	)
	@NotBlank
	private String reason;
}
