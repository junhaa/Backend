package com.bid.auction.domain.refund.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefundRequestOrder {
	@JsonProperty("merchant_uid")
	@NotBlank
	private String merchantUid;
	@NotBlank
	private String reason;
}
