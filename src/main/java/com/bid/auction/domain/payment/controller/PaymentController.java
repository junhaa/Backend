package com.bid.auction.domain.payment.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bid.auction.domain.payment.dto.PaymentRequest.PaymentOrderResult;
import com.bid.auction.domain.payment.dto.PaymentRequest.PaymentVerificationRequest;
import com.bid.auction.domain.payment.dto.PaymentResponse.PaymentCompleteResponse;
import com.bid.auction.domain.payment.service.PaymentCommandService;
import com.bid.auction.domain.payment.service.PaymentQueryService;
import com.bid.auction.global.enums.statuscode.SuccessStatus;
import com.bid.auction.global.response.ApiResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentQueryService paymentQueryService;

	private final PaymentCommandService paymentCommandService;

	@PostMapping("/payment-order")
	public ApiResponse<Void> savePaymentOrder(@RequestBody PaymentOrderResult orderResult) throws
		IamportResponseException,
		IOException {
		paymentCommandService.savePaymentOrder(orderResult);
		return ApiResponse.of(SuccessStatus._ACCEPTED, null);
	}

	@PostMapping("/payment")
	public ApiResponse<PaymentCompleteResponse> completePayment(@RequestBody PaymentVerificationRequest request) throws
		IamportResponseException,
		IOException {
		paymentQueryService.verifyPaymentOrder(request);
		PaymentCompleteResponse response = paymentCommandService.completePayment(request);
		return ApiResponse.onSuccess(response);
	}
}
