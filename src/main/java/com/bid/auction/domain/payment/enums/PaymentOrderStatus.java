package com.bid.auction.domain.payment.enums;

public enum PaymentOrderStatus {
    _COMPLETE("complete"),
    _CANCEL("canceled");
    private final String status;

    PaymentOrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
