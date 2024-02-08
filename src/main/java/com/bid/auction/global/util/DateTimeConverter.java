package com.bid.auction.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeConverter {
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime
			.atZone(ZoneId.systemDefault())
			.toInstant());
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		return date
			.toInstant()
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}
}
