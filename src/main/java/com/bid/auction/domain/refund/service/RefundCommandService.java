package com.bid.auction.domain.refund.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.auction.domain.payment.converter.RefundConverter;
import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.payment.service.IamportService;
import com.bid.auction.domain.payment.service.PaymentQueryService;
import com.bid.auction.domain.refund.dto.RefundRequestOrder;
import com.bid.auction.domain.refund.dto.RefundResponse.PgRefundResponse;
import com.bid.auction.domain.refund.entity.Refund;
import com.bid.auction.domain.refund.entity.RefundRequest;
import com.bid.auction.domain.refund.repository.RefundRepository;
import com.bid.auction.domain.refund.repository.RefundRequestRepository;
import com.bid.auction.global.enums.statuscode.ErrorStatus;
import com.bid.auction.global.exception.GeneralException;
import com.siot.IamportRestClient.exception.IamportResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RefundCommandService {
	private final IamportService iamportService;
	private final RefundRequestRepository refundRequestRepository;
	private final RefundRepository refundRepository;
	private final PaymentQueryService paymentQueryService;

	public RefundRequest saveRefundRequest(
		Payment payment,
		RefundRequestOrder request,
		LocalDateTime requestedAt
	) {
		RefundRequest refundRequest = RefundConverter
			.toRefundRequest(payment, request, requestedAt);
		return refundRequestRepository.save(refundRequest);
	}

	public PgRefundResponse requestRefund(Payment payment, RefundRequest request) throws
		IamportResponseException,
		IOException {
		return iamportService.requestRefund(payment.getPgPaymentUid(), request);
	}

	public Refund saveRefund(PgRefundResponse response) {
		Payment payment = paymentQueryService
			.findPaymentByMerchantUid(response.getMerchantUid());
		RefundRequest request = refundRequestRepository
			.findByPayment(payment)
			.orElseThrow(() -> new GeneralException(ErrorStatus._REFUND_REQUEST_NOT_FOUND));
		request.complete();
		Refund refund = RefundConverter.toRefund(request, response);
		return refundRepository.save(refund);
	}
}
