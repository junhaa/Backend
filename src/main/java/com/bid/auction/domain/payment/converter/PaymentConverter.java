package com.bid.auction.domain.payment.converter;

import static com.bid.auction.global.util.DateTimeConverter.*;

import java.util.Arrays;

import com.bid.auction.domain.payment.dto.PaymentResponse.PaymentCompleteResponse;
import com.bid.auction.domain.payment.dto.PaymentVerificationResponse;
import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.payment.entity.PaymentOrder;
import com.bid.auction.domain.payment.enums.PaymentMethod;
import com.bid.auction.domain.payment.enums.PaymentOrderStatus;
import com.bid.auction.domain.user.User;
import com.bid.auction.global.enums.statuscode.ErrorStatus;
import com.bid.auction.global.exception.GeneralException;

public class PaymentConverter {
	public static PaymentOrder toPaymentOrder(com.siot.IamportRestClient.response.Payment response, User orderingUser) {
		return PaymentOrder.builder()
			.pgUid(response.getImpUid())
			.merchantUid(response.getMerchantUid())
			.paymentOrderAmount(response.getAmount().intValue())
			.orderStatus(convertPaymentStatus(response.getStatus()))
			.requestedAt(toLocalDateTime(response.getPaidAt()))
			.orderingUser(orderingUser)
			.build();
	}

	public static Payment generatePayment(PaymentOrder paymentOrder,
		com.siot.IamportRestClient.response.Payment pgPayment) {
		return Payment.builder()
			.amount(paymentOrder.getPaymentOrderAmount())
			.requestedAt(paymentOrder.getRequestedAt())
			.approvedAt(toLocalDateTime(pgPayment.getPaidAt()))
			.merchantPaymentUid(paymentOrder.getMerchantUid())
			.pgPaymentUid(paymentOrder.getPgUid())
			.paymentMethod(convertPaymentMethod(pgPayment.getPayMethod()))
			.paymentOrder(paymentOrder)
			.paidUser(paymentOrder.getOrderingUser())
			.build();
	}

	public static PaymentVerificationResponse toPaymentVerificationResponse(
		com.siot.IamportRestClient.response.Payment response) {
		return PaymentVerificationResponse.builder()
			.pgPaymentOrderUid(response.getImpUid())
			.merchantPaymentOrderUid(response.getMerchantUid())
			.paymentOrderAmount(response.getAmount().intValue())
			.requestedAt(toLocalDateTime(response.getPaidAt()))
			.build();
	}

	public static PaymentCompleteResponse toPaymentCompleteResponse(Payment payment) {
		return PaymentCompleteResponse.builder()
			.paymentId(payment.getId())
			.paymentAmount(payment.getAmount())
			.paymentMethod(payment.getPaymentMethod())
			.requestedAt(payment.getRequestedAt())
			.approvedAt(payment.getApprovedAt())
			.pgPaymentOrderUid(payment.getPgPaymentUid())
			.merchantPaymentOrderUid(payment.getMerchantPaymentUid())
			.build();
	}

	private static PaymentOrderStatus convertPaymentStatus(String status) {
		return Arrays.stream(PaymentOrderStatus.values())
			.filter(paymentOrderStatus -> paymentOrderStatus.getStatus().equals(status))
			.findFirst()
			.orElseThrow(() -> new GeneralException(ErrorStatus._PAYMENT_ORDER_STATUS_NOT_FOUND));
	}

	private static PaymentMethod convertPaymentMethod(String method) {
		return Arrays.stream(PaymentMethod.values())
			.filter(paymentMethod -> paymentMethod.getMethod().equals(method))
			.findFirst()
			.orElseThrow(() -> new GeneralException(ErrorStatus._PAYMENT_METHOD_NOT_FOUND));
	}
}
