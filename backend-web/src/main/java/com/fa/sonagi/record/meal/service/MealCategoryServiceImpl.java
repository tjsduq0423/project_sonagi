package com.fa.sonagi.record.meal.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fa.sonagi.record.meal.dto.FeedingResDto;
import com.fa.sonagi.record.meal.dto.MealResDto;
import com.fa.sonagi.record.meal.dto.SnackResDto;
import com.fa.sonagi.record.meal.entity.BabyFood;
import com.fa.sonagi.record.meal.entity.BreastFeeding;
import com.fa.sonagi.record.meal.entity.Feeding;
import com.fa.sonagi.record.meal.entity.InfantFormula;
import com.fa.sonagi.record.meal.entity.Milk;
import com.fa.sonagi.record.meal.entity.Snack;
import com.fa.sonagi.record.meal.repository.BabyFoodRepository;
import com.fa.sonagi.record.meal.repository.BreastFeedingRepository;
import com.fa.sonagi.record.meal.repository.FeedingRepository;
import com.fa.sonagi.record.meal.repository.InfantFormulaRepository;
import com.fa.sonagi.record.meal.repository.MilkRepository;
import com.fa.sonagi.record.meal.repository.PumpingBreastRepository;
import com.fa.sonagi.record.meal.repository.SnackRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealCategoryServiceImpl implements MealCategoryService{

	private final BabyFoodRepository babyFoodRepository;
	private final BreastFeedingRepository breastFeedingRepository;
	private final FeedingRepository feedingRepository;
	private final InfantFormulaRepository infantFormulaRepository;
	private final MilkRepository milkRepository;
	private final PumpingBreastRepository pumpingBreastRepository;
	private final SnackRepository snackRepository;

	/**
	 * babyId와 createdDate로 모든 이유식 데이터 찾기
	 */
	@Override
	public List<MealResDto> findAllBabyFood(Long babyId, LocalDate createdDate) {
		List<BabyFood> findBabyFoods = babyFoodRepository.findByBabyIdAndCreatedDate(babyId, createdDate);

		List<MealResDto> babyFoods = findBabyFoods.stream()
			.map(bf -> new MealResDto(bf.getId(), bf.getAmount(), bf.getMemo(), bf.getCreatedTime()))
			.collect(Collectors.toList());

		return babyFoods;
	}

	/**
	 * babyId와 createdDate로 모든 유축 수유 데이터 찾기
	 */
	@Override
	public List<MealResDto> findAllBreastFeeding(Long babyId, LocalDate createdDate) {
		List<BreastFeeding> findBreastFeedings = breastFeedingRepository.findByBabyIdAndCreatedDate(babyId, createdDate);

		List<MealResDto> breastFeedings = findBreastFeedings.stream()
			.map(bf -> new MealResDto(bf.getId(), bf.getAmount(), bf.getMemo(), bf.getCreatedTime()))
			.collect(Collectors.toList());

		return breastFeedings;
	}


	/**
	 * babyId와 createdDate로 모든 수유 데이터 찾기
	 */
	@Override
	public List<FeedingResDto> findAllFeeding(Long babyId, LocalDate createdDate) {
		List<Feeding> findFeedings = feedingRepository.findByBabyIdAndCreatedDate(babyId, createdDate);

		List<FeedingResDto> feedings =findFeedings.stream()
			.map(f -> new FeedingResDto(f.getId(), f.getLeftStartTime(), f.getRightStartTime(),
				f.getLeftEndTime(), f.getRightEndTime(), f.getMemo()))
			.collect(Collectors.toList());
		return feedings;
	}


	/**
	 * babyId와 createdDate로 모든 분유 데이터 찾기
	 */
	@Override
	public List<MealResDto> findAllInfantFormula(Long babyId, LocalDate createdDate) {
		List<InfantFormula> findInfantFormulas = infantFormulaRepository.findByBabyIdAndCreatedDate(babyId, createdDate);

		List<MealResDto> infantFormulas = findInfantFormulas.stream()
			.map(inf -> new MealResDto(inf.getId(), inf.getAmount(), inf.getMemo(), inf.getCreatedTime()))
			.collect(Collectors.toList());

		return infantFormulas;
	}


	/**
	 * babyId와 createdDate로 모든 우유 데이터 찾기
	 */
	@Override
	public List<MealResDto> findAllMilk(Long babyId, LocalDate createdDate) {
		List<Milk> findMilks = milkRepository.findByBabyIdAndCreatedDate(babyId, createdDate);

		List<MealResDto> milks = findMilks.stream()
			.map(m -> new MealResDto(m.getId(), m.getAmount(), m.getMemo(), m.getCreatedTime()))
			.collect(Collectors.toList());

		return milks;
	}


	/**
	 * babyId와 createdDate로 모든 간식 데이터 찾기
	 */
	@Override
	public List<SnackResDto> findAllSnack(Long babyId, LocalDate createdDate) {
		List<Snack> findSnacks = snackRepository.findByBabyIdAndCreatedDate(babyId, createdDate);

		List<SnackResDto> snacks = findSnacks.stream()
			.map(s -> new SnackResDto(s.getId(), s.getMemo(), s.getCreatedTime()))
			.collect(Collectors.toList());

		return snacks;
	}


	/**
	 * babyId와 createdDate로 모든 유축 데이터 찾기
	 */
	@Override
	public List<MealResDto> findAllPumpingBreast(Long babyId, LocalDate createdDate) {
		return null;
	}
}
