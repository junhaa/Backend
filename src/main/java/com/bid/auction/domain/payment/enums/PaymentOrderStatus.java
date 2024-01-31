package com.bid.auction.domain.payment.enums;

public enum PaymentOrderStatus {
	_READY("ready"),
	_PAID("paid"),
	_FAILED("failed"),
	_CANCELED("canceled"),
	_ALL("all");
	private final String status;

	PaymentOrderStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
