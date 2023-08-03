package com.fa.sonagi.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemoResDto {
	private Long id;
	private Long userId;
	private String memo;
}