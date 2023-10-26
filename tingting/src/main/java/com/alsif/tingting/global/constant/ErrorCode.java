package com.alsif.tingting.global.constant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	BAD_REQUEST_ORDERBY(HttpStatus.BAD_REQUEST, "잘못된 매개변수(orderBy) 요청"),
	BAD_REQUEST_CONCERT_SEQ(HttpStatus.BAD_REQUEST, "잘못된 매개변수(concertSeq) 요청"),
	BAD_REQUEST_CONCERT_SECTION_INFO(HttpStatus.BAD_REQUEST, "잘못된 매개변수(concertDetailSeq, concertHallSeq, target) 요청"),
	BAD_REQUEST_CONCERT_HALL_SEAT_SEQ(HttpStatus.BAD_REQUEST, "잘못된 매개변수(concertHallSeatSeq) 요청"),
	NOT_AVAILABLE_SEAT(HttpStatus.CONFLICT, "예매 불가능");

	private final HttpStatus httpStatus;
	private final String message;
}