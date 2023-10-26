package com.alsif.tingting.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alsif.tingting.book.service.BookService;
import com.alsif.tingting.concert.dto.concerthall.ConcertHallPatternResponseDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSeatBookRequestDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSeatBookResponseDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoRequestDto;
import com.alsif.tingting.concert.dto.concerthall.ConcertSectionSeatInfoResponseDto;
import com.alsif.tingting.global.dto.ErrorResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "콘서트 예매", description = "콘서트 예매 API")
public class BookController {

	private final BookService bookService;

	@Operation(summary = "콘서트장 정보 조회")
	@Parameters(value = {
		@Parameter(required = true, name = "concertSeq", description = "콘서트 PK")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{concertSeq}")
	ResponseEntity<ConcertHallPatternResponseDto> findConcertPattern(
		@PathVariable("concertSeq") Long concertSeq) {
		log.info("===== 콘서트장 정보 조회 요청 시작, url={}, concertSeq: {} =====",
			"/concerts", concertSeq);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertHallPatternResponseDto concertHallPatternResponseDto = bookService.findConcertPattern(concertSeq);
		stopWatch.stop();

		log.info("===== 콘서트 정보 조회 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertHallPatternResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "콘서트장 섹션별 좌석 정보 조회")
	@Parameters(value = {
		@Parameter(required = true, name = "concertDetailSeq", description = "콘서트 상세 PK (ex. 1)"),
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{concertDetailSeq}/section")
	ResponseEntity<List<ConcertSectionSeatInfoResponseDto>> findConcertSectionSeatInfoList(
		@PathVariable("concertDetailSeq") Long concertDetailSeq, ConcertSectionSeatInfoRequestDto requestDto) {
		log.info("===== 콘서트장 섹션별 좌석 정보 조회 요청 시작, url={}, concertDetailSeq: {}, {} =====",
			"/concerts", concertDetailSeq, requestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<ConcertSectionSeatInfoResponseDto> concertSectionSeatInfoResponseDtos
			= bookService.findConcertSectionSeatInfoList(concertDetailSeq, requestDto);
		stopWatch.stop();

		log.info("===== 콘서트장 섹션별 좌석 정보 조회 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertSectionSeatInfoResponseDtos, HttpStatus.OK);
	}

	@Operation(summary = "특정 좌석의 예매 가능 여부 확인")
	@Parameters(value = {
		@Parameter(required = true, name = "concertDetailSeq", description = "콘서트 상세 PK (ex. 1)"),
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "400", description = "잘못된 매개변수 요청",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	})
	@GetMapping("/{concertDetailSeq}/seat")
	ResponseEntity<ConcertSeatBookResponseDto> isSeatAvailable(
		@PathVariable("concertDetailSeq") Long concertDetailSeq, ConcertSeatBookRequestDto requestDto) {
		log.info("===== 특정 좌석의 예매 가능 여부 확인 요청 시작, url={}, concertDetailSeq: {}, {} =====",
			"/concerts", concertDetailSeq, requestDto.toString());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConcertSeatBookResponseDto concertSeatBookResponseDto
			= bookService.isSeatAvailable(concertDetailSeq, requestDto);
		stopWatch.stop();

		log.info("===== 특정 좌석의 예매 가능 여부 확인 요청 종료, 소요시간: {} milliseconds =====", stopWatch.getTotalTimeMillis());
		return new ResponseEntity<>(concertSeatBookResponseDto, HttpStatus.OK);
	}
}