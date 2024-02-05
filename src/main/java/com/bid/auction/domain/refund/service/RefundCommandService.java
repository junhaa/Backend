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
import com.bid.auction.domain.refund.dto.RefundResponse.RefundSuccessResponse;
import com.bid.auction.domain.refund.entity.Refund;
import com.bid.auction.domain.refund.entity.RefundRequest;
import com.bid.auction.domain.refund.repository.RefundRepository;
import com.bid.auction.domain.refund.repository.RefundRequestRepository;
import com.siot.IamportRestClient.exception.IamportResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RefundCommandService {
	private final IamportService iamportService;
	private final PaymentQueryService paymentQueryService;
	private final RefundRequestRepository refundRequestRepository;
	private final RefundRepository refundRepository;

	public RefundSuccessResponse refund(RefundRequestOrder request, LocalDateTime requestedAt)
		throws IamportResponseException, IOException {
		String merchantUid = request.getMerchantUid();
		Payment payment = paymentQueryService.findPaymentByMerchantUid(merchantUid);

		RefundRequest refundRequest = saveRefundRequest(payment, request, requestedAt);

		PgRefundResponse pgRefundResponse = iamportService
			.requestRefund(payment.getPgPaymentUid(), refundRequest);
		Refund refund = saveRefund(refundRequest, pgRefundResponse);

		return RefundConverter.toRefundSuccessResponse(refund);
	}

	private RefundRequest saveRefundRequest(
		Payment payment,
		RefundRequestOrder request,
		LocalDateTime requestedAt
	) {
		RefundRequest refundRequest = RefundConverter
			.toRefundRequest(payment, request, requestedAt);
		return refundRequestRepository.save(refundRequest);
	}

	private Refund saveRefund(RefundRequest request, PgRefundResponse response) {
		Refund refund = RefundConverter.toRefund(request, response);
		return refundRepository.save(refund);
	}
}
