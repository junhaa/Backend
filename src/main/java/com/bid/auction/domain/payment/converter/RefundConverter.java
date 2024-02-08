package com.bid.auction.domain.payment.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.refund.domain.entity.Refund;
import com.bid.auction.domain.refund.domain.entity.RefundRequest;
import com.bid.auction.domain.refund.domain.enums.RefundStatus;
import com.bid.auction.domain.refund.presentation.dto.RefundRequestOrder;
import com.bid.auction.domain.refund.presentation.dto.RefundResponse.PgRefundResponse;
import com.bid.auction.domain.refund.presentation.dto.RefundResponse.RefundSuccessResponse;
import com.bid.auction.global.util.DateTimeConverter;
import com.siot.IamportRestClient.request.CancelData;

public class RefundConverter {
	public static RefundRequest toRefundRequest(
		Payment payment,
		RefundRequestOrder request,
		LocalDateTime requestedAt
	) {
		return RefundRequest.builder()
			.amount(payment.getAmount())
			.refundMethod(payment.getPaymentMethod())
			.status(RefundStatus.FAILED)
			.reason(request.getReason())
			.requestedAt(requestedAt)
			.requestedUser(payment.getPaidUser())
			.payment(payment)
			.build();
	}

	public static PgRefundResponse toPgRefundResponse(
		com.siot.IamportRestClient.response.Payment payment,
		LocalDateTime requestedAt
	) {
		return PgRefundResponse.builder()
			.pgUid(payment.getImpUid())
			.merchantUid(payment.getMerchantUid())
			.amount(payment.getAmount().intValue())
			.reason(payment.getCancelReason())
			.refundedAt(DateTimeConverter
				.toLocalDateTime(payment.getCancelledAt()))
			.refundRequestedAt(requestedAt)
			.build();
	}

	public static CancelData toCancelDate(String pgUid, RefundRequest request) {
		BigDecimal amount = BigDecimal.valueOf(request.getAmount());

		CancelData cancelData = new CancelData(
			pgUid,
			true,
			amount
		);
		cancelData.setReason(request.getReason());
		cancelData.setChecksum(amount);
		return cancelData;
	}

	public static Refund toRefund(RefundRequest request, PgRefundResponse response) {
		return Refund.builder()
			.amount(response.getAmount())
			.reason(response.getReason())
			.refundedAt(response.getRefundedAt())
			.refundRequestedAt(response.getRefundRequestedAt())
			.request(request)
			.build();
	}

	public static RefundSuccessResponse toRefundSuccessResponse(Refund refund) {
		return RefundSuccessResponse.builder()
			.refundId(refund.getId())
			.pgUid(refund.getRequest().getPayment().getPgPaymentUid())
			.merchantUid(refund.getRequest().getPayment().getMerchantPaymentUid())
			.amount(refund.getAmount())
			.refundedAt(refund.getRefundedAt())
			.build();
	}
}
