package com.bid.auction.global.response;

import com.bid.auction.global.enums.statuscode.BaseCode;
import com.bid.auction.global.enums.statuscode.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

	@Schema(
		type = "boolean",
		description = "요청 처리 성공 여부",
		example = "true"
	)
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	@Schema(
		type = "string",
		description = "서비스 정의 응답 코드",
		example = "COMMON200"
	)
	private final String code;
	@Schema(
		type = "string",
		description = "응답 메시지",
		example = "성공입니다."
	)
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	// 성공한 경우 응답 생성

	public static <T> ApiResponse<T> onSuccess(T result) {
		return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
	}

	public static <T> ApiResponse<T> of(boolean isSuccess, BaseCode code, T result) {
		return new ApiResponse<>(isSuccess, code.getCode(), code.getMessage(), result);
	}

	// 실패한 경우 응답 생성
	public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
		return new ApiResponse<>(false, code, message, data);
	}
}
