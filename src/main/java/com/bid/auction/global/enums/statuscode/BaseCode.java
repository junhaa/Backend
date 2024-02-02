package com.bid.auction.global.enums.statuscode;

import org.springframework.http.HttpStatus;

public interface BaseCode {
	String getCode();

	String getMessage();

	HttpStatus getStatus();

	Integer getStatusValue();
}
