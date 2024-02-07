package com.bid.auction.domain.refund;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "환불 관련 API")
public class RefundController {
	private final PaymentQueryService paymentQueryService;
	private final RefundCommandService refundCommandService;

	@PostMapping("/refund")
	@Operation(
		summary = "환불 처리 API",
		description = """
			클라이언트로부터 서비스 결제 고유 코드(merchant_uid)를 받아
			PG사 서버에 결제 취소를 요청하고 환불을 완료하는 작업을 수행하는
			API입니다.""")
	@Parameters({
		@Parameter(
			name = "merchant_uid",
			description = "서비스 서버에서 부여하는 결제 데이터 고유번호, {User PK}\\_{Payment Type}\\_{uid} 형식",
			example = "1_productOrder_uuid18"),
		@Parameter(
			name = "reason",
			description = "환불 사유",
			example = "단순 변심")
	}
	)
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "200",
			description = "정상 환불 처리"
		),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "PAYMENT4001",
			description = "해당 결제 내역이 존재하지 않습니다.",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schemaProperties = {
					@SchemaProperty(
						name = "isSuccess",
						schema = @Schema(type = "boolean", example = "false")
					),
					@SchemaProperty(
						name = "code",
						schema = @Schema(type = "string", example = "PAYMENT4001")
					),
					@SchemaProperty(
						name = "message",
						schema = @Schema(type = "string", example = "해당 결제 내역이 존재하지 않습니다.")
					),
					@SchemaProperty(
						name = "result",
						schema = @Schema(type = "list", example = "{}")
					)
				}
			)
		)
	})
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
