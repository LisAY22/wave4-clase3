package com.guatemaltek.postgres.clase3.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class AllowedCurrencyValidator implements ConstraintValidator<AllowedCurrency, String> {

  private static final Set<String> ALLOWED = Set.of("GTQ", "USD", "MXN", "EUR");

  @Override
  public boolean isValid(String value, ConstraintValidatorContext ctx) {
    return value != null && ALLOWED.contains(value.toUpperCase());
  }
}
