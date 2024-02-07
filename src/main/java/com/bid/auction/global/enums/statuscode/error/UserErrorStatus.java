package com.bid.auction.global.enums.statuscode.error;

import org.springframework.http.HttpStatus;

import com.bid.auction.global.enums.statuscode.BaseCode;

public enum UserErrorStatus implements BaseCode {
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "해당 사용자가 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	UserErrorStatus(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getStatus() {
		return httpStatus;
	}

	@Override
	public Integer getStatusValue() {
		return httpStatus.value();
	}
}
