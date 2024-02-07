package com.bid.auction.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI bidApi() {
		Info info = new Info().title("Bid API Documentation").description("Bid API 명세서").version("1.0.0");
		return new OpenAPI().info(info).addServersItem(new Server().url("/"));
	}
}


