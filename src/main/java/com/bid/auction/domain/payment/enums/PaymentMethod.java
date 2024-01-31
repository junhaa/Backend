package com.bid.auction.domain.payment.enums;

public enum PaymentMethod {
	_CARD("card"),
	_BANK_TRANSFER("trans"),
	_VIRTUAL_ACCOUNT("vbank"),
	_KAKAO_PAY("kakaopay"),
	_TOSS_PAY("tosspay"),
	_NAVER_PAY("naverpay");

	private final String method;

	PaymentMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}
}
