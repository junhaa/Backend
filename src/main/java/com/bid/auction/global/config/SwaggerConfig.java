package com.bid.auction.global.config;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import com.bid.auction.global.dto.ExampleHolder;
import com.bid.auction.global.enums.statuscode.ApiErrorCode;
import com.bid.auction.global.enums.statuscode.BaseCode;
import com.bid.auction.global.response.ApiResponse;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI bidApi() {
		Info info = new Info().title("Bid API Documentation").description("Bid API 명세서").version("1.0.0");
		return new OpenAPI().info(info).addServersItem(new Server().url("/"));
	}

	@Bean
	public OperationCustomizer customizer() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			ApiErrorCode apiErrorCode = handlerMethod.getMethodAnnotation(ApiErrorCode.class);

			if (apiErrorCode != null) {
				generateErrorCodeResponse(operation, apiErrorCode.value());
			}

			return operation;
		};
	}

	private void generateErrorCodeResponse(Operation operation, Class<? extends BaseCode> type) {
		ApiResponses responses = operation.getResponses();
		BaseCode[] errorCodes = type.getEnumConstants();

		Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(errorCodes)
			.map(errorCode -> ExampleHolder.builder()
				.example(getSwaggerExample(errorCode.getMessage(), errorCode))
				.code(errorCode.getStatusValue())
				.name(errorCode.getCode())
				.build())
			.collect(groupingBy(ExampleHolder::getCode));

		addExampleToResponses(responses, statusWithExampleHolders);
	}

	private Example getSwaggerExample(String value, BaseCode baseCode) {

		ApiResponse<Object> response = ApiResponse.onFailure(baseCode.getCode(), baseCode.getMessage(), null);
		Example example = new Example();
		example.description(value);
		example.setValue(response);
		return example;
	}

	private void addExampleToResponses(ApiResponses responses,
		Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
		statusWithExampleHolders.forEach((status, v) -> {
			Content content = new Content();
			MediaType mediaType = new MediaType();
			io.swagger.v3.oas.models.responses.ApiResponse apiResponse =
				new io.swagger.v3.oas.models.responses.ApiResponse();
			v.forEach(exampleHolder -> mediaType.addExamples(exampleHolder.getName(), exampleHolder.getExample()));
			content.addMediaType("application/json", mediaType);
			apiResponse.setContent(content);
			responses.addApiResponse(status.toString(), apiResponse);
		});
	}
}


