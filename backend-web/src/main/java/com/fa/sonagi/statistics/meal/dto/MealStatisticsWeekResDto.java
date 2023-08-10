package com.fa.sonagi.statistics.meal.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealStatisticsWeekResDto {
	private Map<String, MealStatisticsDayForWeekDto> mealStatistics;
	private Long cnt;
	private Long amount;
	private Long cntPercent;
	private Long lastWeekCntPercent;
	private Long amountPercent;
	private Long lastWeekAmountPercent;

	public MealStatisticsWeekResDto() {
		this.mealStatistics = new HashMap<>();
	}
}
