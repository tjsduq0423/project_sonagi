package com.fa.sonagi.record.meal.dto;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FeedingResDto {
	private Long id;
	private Time leftStartTime;
	private Time rightStartTime;
	private Time leftEndTime;
	private Time rightEndTime;
	private String memo;
}