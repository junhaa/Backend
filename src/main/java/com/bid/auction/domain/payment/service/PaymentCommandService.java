package com.bid.auction.domain.payment.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.domain.payment.converter.PaymentConverter;
import com.bid.auction.domain.payment.dto.PaymentRequest.PaymentOrderResult;
import com.bid.auction.domain.payment.dto.PaymentRequest.PaymentVerificationRequest;
import com.bid.auction.domain.payment.dto.PaymentResponse.PaymentCompleteResponse;
import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.payment.entity.PaymentOrder;
import com.bid.auction.domain.payment.repository.PaymentOrderRepository;
import com.bid.auction.domain.payment.repository.PaymentRepository;
import com.bid.auction.domain.user.domain.entity.User;
import com.bid.auction.domain.user.domain.service.UserQueryService;
import com.siot.IamportRestClient.exception.IamportResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentCommandService {
	private final IamportService iamportService;
	private final PaymentOrderRepository paymentOrderRepository;
	private final PaymentRepository paymentRepository;
	private final UserQueryService userQueryService;
	private final PaymentQueryService paymentQueryService;

	public void savePaymentOrder(PaymentOrderResult orderResult) throws IamportResponseException, IOException {
		com.siot.IamportRestClient.response.Payment paymentOrderResult = iamportService.getPaymentByPaymentOrderResult(
			orderResult);

		Long userId = parseUserIdFromMerchantOrderUid(orderResult.getMerchantPaymentOrderUid());

		User orderingUser = userQueryService.findUserById(userId);

		PaymentOrder paymentOrder = PaymentConverter.toPaymentOrder(paymentOrderResult, orderingUser);
		paymentOrderRepository.save(paymentOrder);
	}

	public PaymentCompleteResponse completePayment(PaymentVerificationRequest request) throws
		IamportResponseException,
		IOException {
		PaymentOrder paymentOrder = paymentQueryService.findPaymentOrderByMerchantUid(request.getMerchantUid());
		com.siot.IamportRestClient.response.Payment pgPayment = iamportService.getPaymentByPgUid(request.getImpUid());
		Payment payment = paymentRepository.save(PaymentConverter.generatePayment(paymentOrder, pgPayment));
		paymentOrder.orderComplete();
		return PaymentConverter.toPaymentCompleteResponse(payment);
	}

	private Long parseUserIdFromMerchantOrderUid(String merchantOrderUid) {
		String userId = merchantOrderUid.split("_")[0];
		return Long.valueOf(userId);
	}
}
