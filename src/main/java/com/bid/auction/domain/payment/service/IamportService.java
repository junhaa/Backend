package com.bid.auction.domain.payment.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bid.auction.domain.payment.converter.PaymentConverter;
import com.bid.auction.domain.payment.dto.PaymentRequest.PaymentOrderResult;
import com.bid.auction.domain.payment.dto.PaymentRequest.PaymentVerificationRequest;
import com.bid.auction.domain.payment.dto.PaymentVerificationResponse;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;

import jakarta.annotation.PostConstruct;

@Service
public class IamportService {
	@Value("${iamport.store-id}")
	private String iamportStoreId;
	@Value("${iamport.api-key}")
	private String iamportApiKey;
	@Value("${iamport.api-secret}")
	private String iamportApiSecret;
	private IamportClient client;

	@PostConstruct
	public void init() {
		client = new IamportClient(iamportApiKey, iamportApiSecret);
	}

	public Payment getPaymentByPaymentOrderResult(PaymentOrderResult orderResult) throws
		IamportResponseException,
		IOException {
		return client.paymentByImpUid(orderResult.getPgPaymentOrderUid()).getResponse();
	}

	public Payment getPaymentByPgUid(String uid) throws IamportResponseException, IOException {
		return client.paymentByImpUid(uid).getResponse();
	}

	public PaymentVerificationResponse getPaymentVerificationResponse(PaymentVerificationRequest request) throws
		IamportResponseException,
		IOException {
		Payment response = client.paymentByImpUid(request.getImpUid()).getResponse();
		return PaymentConverter.toPaymentVerificationResponse(response);
	}
}
