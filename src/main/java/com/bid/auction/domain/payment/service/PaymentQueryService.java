package com.bid.auction.domain.payment.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.domain.payment.dto.PaymentRequest.PaymentVerificationRequest;
import com.bid.auction.domain.payment.dto.PaymentVerificationResponse;
import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.payment.entity.PaymentOrder;
import com.bid.auction.domain.payment.repository.PaymentOrderRepository;
import com.bid.auction.domain.payment.repository.PaymentRepository;
import com.bid.auction.global.enums.statuscode.ErrorStatus;
import com.bid.auction.global.exception.GeneralException;
import com.siot.IamportRestClient.exception.IamportResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentQueryService {
	private final IamportService iamportService;
	private final PaymentOrderRepository paymentOrderRepository;
	private final PaymentRepository paymentRepository;

	public void verifyPaymentOrder(PaymentVerificationRequest request) throws IamportResponseException, IOException {
		PaymentVerificationResponse verificationResponse = iamportService.getPaymentVerificationResponse(request);
		String uid = verificationResponse.merchantPaymentOrderUid();
		PaymentOrder paymentOrder = findPaymentOrderByMerchantUid(uid);
		verifyActualAmountEqualOrderAmount(verificationResponse, paymentOrder);
	}

	public PaymentOrder findPaymentOrderByMerchantUid(String uid) {
		return paymentOrderRepository.findByMerchantUid(uid)
			.orElseThrow(() -> new GeneralException(ErrorStatus._PAYMENT_ORDER_NOT_FOUND));
	}

	public Payment findPaymentByMerchantUid(String uid) {
		return paymentRepository.findByMerchantPaymentUid(uid)
			.orElseThrow(() -> new GeneralException(ErrorStatus._PAYMENT_NOT_FOUND));
	}

	private void verifyActualAmountEqualOrderAmount(PaymentVerificationResponse response, PaymentOrder paymentOrder) {
		Integer orderAmount = paymentOrder.getPaymentOrderAmount();
		Integer actualAmount = response.paymentOrderAmount();

		if (!actualAmount.equals(orderAmount)) {
			throw new GeneralException(ErrorStatus._UNEQUAL_ACTUAL_PAYMENT_AMOUNT);
		}
	}
}
