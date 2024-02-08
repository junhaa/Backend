package com.bid.auction.global.enums.statuscode.error;

import org.springframework.http.HttpStatus;

import com.bid.auction.global.enums.statuscode.BaseCode;

public enum PaymentErrorStatus implements BaseCode {
	// payment
	UNEQUAL_ACTUAL_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST, "PAYMENT4001", "실 결제 금액과 결제 주문 금액이 다릅니다. 위변조 되었을 수 있습니다."),
	PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PAYMENT4002", "해당 결제 내역이 존재하지 않습니다."),

	// payment status
	PAYMENT_METHOD_NOT_FOUND(HttpStatus.BAD_REQUEST, "PAYMENT_METHOD4001", "유효하지 않은 결제 수단입니다."),

	// payment order
	PAYMENT_ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "PAYMENT_ORDER4001", "해당 결제 주문이 존재하지 않습니다."),

	// payment order status
	PAYMENT_ORDER_STATUS_NOT_FOUND(HttpStatus.BAD_REQUEST, "PAYMENT_ORDER_STATUS4001", "유효하지 않은 결제 주문 상태입니다.");
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	PaymentErrorStatus(HttpStatus httpStatus, String code, String message) {
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
