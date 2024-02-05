package com.bid.auction.global.enums.statuscode;

import org.springframework.http.HttpStatus;

public enum ErrorStatus implements BaseCode {
	// common
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	// user
	_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "해당 사용자가 존재하지 않습니다."),

	// payment
	_UNEQUAL_ACTUAL_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST, "PAYMENT4001", "실 결제 금액과 결제 주문 금액이 다릅니다. 위변조 되었을 수 있습니다."),

	// payment status
	_PAYMENT_METHOD_NOT_FOUND(HttpStatus.BAD_REQUEST, "PAYMENT_METHOD4001", "유효하지 않은 결제 수단입니다."),

	// payment order
	_PAYMENT_ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "PAYMENT_ORDER4001", "해당 결제 주문이 존재하지 않습니다."),

	// payment order status
	_PAYMENT_ORDER_STATUS_NOT_FOUND(HttpStatus.BAD_REQUEST, "PAYMENT_ORDER_STATUS4001", "유효하지 않은 결제 주문 상태입니다."),

	// auction post
	_IMAGE_FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "AUCTION_POST4001", "이미지 파일의 크기가 너무 큽니다."),
	_BUYOUT_PRICE_NOT_VALID(HttpStatus.BAD_REQUEST, "AUCTION_POST4002", "즉시 구매 가격은 최소 입찰가보다 높아야 합니다."),
	_INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "AUCTION_POST4003", "잘못된 카테고리 입니다."),
	_NOT_POSITIVE_NUMBER(HttpStatus.BAD_REQUEST, "AUCTIONPOST4004", "음수일 수 없습니다."),
	_INVALID_ENUM_VALUE(HttpStatus.BAD_REQUEST, "AUCTIONPOST4005", "지원하지 않는 필드 값입니다."),
	_AUCTION_POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUCTIONPOST4006", "유효하지 않은 상품 게시물입니다."),
	_INVALID_ENUM_VALUE(HttpStatus.BAD_REQUEST, "AUCTIONPOST4005", "지원하지 않는 필드 값입니다."),

	REDIS_ERROR(HttpStatus.BAD_REQUEST, "REDIS ERROR", "REDIS ERROR"),
	INVALID_JWT(HttpStatus.BAD_REQUEST, "INVALID_JWT", "INVALID_JWT"),
	EXPIRED_JWT(HttpStatus.BAD_REQUEST, "EXPIRED_JWT", "EXPIRED_JWT"),
	INVALID_USER_NUM(HttpStatus.BAD_REQUEST, "NVALID_USER_NUM", "NVALID_USER_NUM"),
	INVALID_USER_PW(HttpStatus.BAD_REQUEST, "INVALID_USER_PW", "INVALID_USER_PW"),

	NOT_EXIST_REFRESH_JWT(HttpStatus.BAD_REQUEST, "NOT_EXIST_REFRESH_JWT", "NOT_EXIST_REFRESH_JWT");
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ErrorStatus(HttpStatus httpStatus, String code, String message) {
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

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
