package com.fa.sonagi.record.meal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fa.sonagi.record.meal.entity.Milk;

public interface MilkRepository extends JpaRepository<Milk, Long> {

	List<Milk> findByBabyIdAndCreatedDate(Long babyId, LocalDate createdDate);
}