package com.fa.sonagi.record.meal.service;

import com.fa.sonagi.record.meal.dto.MealPostDto;
import com.fa.sonagi.record.meal.dto.MealPutDto;
import com.fa.sonagi.record.meal.entity.InfantFormula;

public interface InfantFormulasService {
  InfantFormula findInfantFormulaById(Long id);

  void registInfantFormula(MealPostDto mealPostDto);

  void updateInfantFormula(MealPutDto mealPutDto);

  void deleteInfantFormulaById(Long id);
}
