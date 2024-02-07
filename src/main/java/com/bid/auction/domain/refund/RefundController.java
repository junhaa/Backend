package com.bid.auction.domain.refund;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bid.auction.domain.payment.converter.RefundConverter;
import com.bid.auction.domain.payment.entity.Payment;
import com.bid.auction.domain.payment.service.PaymentQueryService;
import com.bid.auction.domain.refund.dto.RefundRequestOrder;
import com.bid.auction.domain.refund.dto.RefundResponse.PgRefundResponse;
import com.bid.auction.domain.refund.dto.RefundResponse.RefundSuccessResponse;
import com.bid.auction.domain.refund.entity.Refund;
import com.bid.auction.domain.refund.entity.RefundRequest;
import com.bid.auction.domain.refund.service.RefundCommandService;
import com.bid.auction.global.response.ApiResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "환불 관련 API")
public class RefundController {
	private final PaymentQueryService paymentQueryService;
	private final RefundCommandService refundCommandService;

	@PostMapping("/refund")
	public ApiResponse<RefundSuccessResponse> refund(@RequestBody RefundRequestOrder request) throws
		IamportResponseException,
		IOException {
		Payment payment = paymentQueryService.findPaymentByMerchantUid(request.getMerchantUid());
		RefundRequest refundRequest = refundCommandService.saveRefundRequest(payment, request, LocalDateTime.now());
		PgRefundResponse pgRefundResponse = refundCommandService.requestRefund(payment, refundRequest);
		Refund refund = refundCommandService.saveRefund(pgRefundResponse);
		RefundSuccessResponse response = RefundConverter.toRefundSuccessResponse(refund);
		return ApiResponse.onSuccess(response);
	}
}
