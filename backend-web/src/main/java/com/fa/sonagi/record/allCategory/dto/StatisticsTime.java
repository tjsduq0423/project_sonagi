package com.fa.sonagi.record.allCategory.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsTime {
	LocalDate createdDate;
	LocalTime createdTime;
	LocalTime endTime;
}
