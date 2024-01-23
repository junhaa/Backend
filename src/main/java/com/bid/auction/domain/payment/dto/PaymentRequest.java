package com.bid.auction.domain.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class PaymentRequest {
    @Getter
    public static class PaymentVerificationRequest{
        @NotNull
        private String impUid;
        @NotNull
        private String merchantUid;
    }
}