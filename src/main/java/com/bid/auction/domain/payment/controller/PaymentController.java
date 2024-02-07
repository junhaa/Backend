package com.bid.auction.domain.payment.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
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
@Tag(name = "결제 관련 API")
public class PaymentController {
	private final PaymentQueryService paymentQueryService;

	private final PaymentCommandService paymentCommandService;

	@PostMapping("/payment-order")
	@Operation(
		summary = "PG 서버에서 결제 요청 결과를 받는 웹훅 API",
		description = """
			클라이언트에서 결제를 요청할 경우, PG사 서버에 그 결과를 받는 웹훅 API입니다.
			프론트에서 이용하는 API가 아닙니다. 다만, 프론트에서 merchant_uid의 포맷을
			잘못 보낼 경우 이 API에서 예외가 발생할 수 있습니다.
			"""
	)
	@Parameters({
		@Parameter(
			name = "imp_uid",
			description = "PG사에서 부여하는 결제 데이터 고유번호",
			example = "imp_245402750188"),
		@Parameter(name = "merchant_uid",
			description = "서비스 서버에서 부여하는 결제 데이터 고유번호, {User PK}\\_{Payment Type}\\_{uid} 형식",
			example = "1_productOrder_uuid18"),
		@Parameter(name = "status",
			description = "PG사 결제 요청 처리 상태",
			example = "paid")
	})
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "COMMON204",
			description = "응답 데이터 없음, 정상 처리",
			content = @Content(schema = @Schema(implementation = ApiResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "USER4001",
			description = "merchant_uid의 형식이 잘못 되었거나 해당 ID를 가진 사용자가 존재하지 않음",
			content = @Content(schema = @Schema(implementation = ApiResponse.class)))
	})
	public ApiResponse<Void> savePaymentOrder(@RequestBody PaymentOrderResult orderResult) throws
		IamportResponseException,
		IOException {
		paymentCommandService.savePaymentOrder(orderResult);
		return ApiResponse.of(true, SuccessStatus._ACCEPTED, null);
	}

	@Operation(summary = "결제 완료 처리 API",
		description = """
			클라이언트에서 PG사로부터 응답 받은 결과를 바탕으로 서비스 서버에 결제
			완료 처리를 요청하는 API입니다. 결제 요청 금액과 실 결제 금액이 같은 지
			비교하는 결제 위변조 검증 과정을 포함합니다.
			""")
	@Parameters({
		@Parameter(
			name = "imp_uid",
			description = "PG사에서 부여하는 결제 데이터 고유번호",
			example = "imp_245402750188"),
		@Parameter(
			name = "merchant_uid",
			description = "서비스 서버에서 부여하는 결제 데이터 고유번호, {User PK}_{Payment Type}_{uid} 형식",
			example = "1_productOrder_uuid18")
	})
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "200",
			description = "정상 결제 완료 처리"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "PAYMENT_ORDER4001",
			description = "해당 결제 주문이 존재하지 않습니다.",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schemaProperties = {
					@SchemaProperty(
						name = "isSuccess",
						schema = @Schema(type = "boolean", example = "false")
					),
					@SchemaProperty(
						name = "code",
						schema = @Schema(type = "string", example = "PAYMENT_ORDER4001")
					),
					@SchemaProperty(
						name = "message",
						schema = @Schema(type = "string", example = "해당 결제 주문이 존재하지 않습니다.")
					),
					@SchemaProperty(
						name = "result",
						schema = @Schema(type = "list", example = "{}")
					)
				}
			)
		),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "PAYMENT4001",
			description = "실 결제 금액과 결제 주문 금액이 다릅니다. 위변조 되었을 수 있습니다.",
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
						schema = @Schema(type = "string",
							example = "실 결제 금액과 결제 주문 금액이 다릅니다. 위변조 되었을 수 있습니다.")
					),
					@SchemaProperty(
						name = "result",
						schema = @Schema(type = "list", example = "{}")
					)
				}
			)
		)
	})
	@PostMapping("/payment")
	public ApiResponse<PaymentCompleteResponse> completePayment(@RequestBody PaymentVerificationRequest request) throws
		IamportResponseException,
		IOException {
		paymentQueryService.verifyPaymentOrder(request);
		PaymentCompleteResponse response = paymentCommandService.completePayment(request);
		return ApiResponse.onSuccess(response);
	}
}
