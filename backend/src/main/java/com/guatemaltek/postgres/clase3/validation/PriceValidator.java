package com.guatemaltek.postgres.clase3.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<Price, BigDecimal> {

  private static final BigDecimal MAX = new BigDecimal("1000000");

  @Override
  public boolean isValid(BigDecimal value, ConstraintValidatorContext ctx) {
    if (value == null) {
      return false;
    }
    return value.scale() <= 2 && value.compareTo(BigDecimal.ZERO) > 0 && value.compareTo(MAX) <= 0;
  }
}
