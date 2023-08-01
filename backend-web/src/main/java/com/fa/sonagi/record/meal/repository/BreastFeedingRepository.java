package com.fa.sonagi.record.meal.repository;

import java.time.LocalDate;
import java.util.List;

import com.fa.sonagi.record.meal.entity.BreastFeeding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreastFeedingRepository extends JpaRepository<BreastFeeding, Long> {

	List<BreastFeeding> findByBabyIdAndCreatedDate(Long babyId, LocalDate createdDate);
}
