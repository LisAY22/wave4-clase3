package com.guatemaltek.postgres.clase3.api.dto;


import com.guatemaltek.postgres.clase3.validation.AllowedCurrency;
import com.guatemaltek.postgres.clase3.validation.Price;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank @Size(max = 120) String name,
    @Price BigDecimal price,
    @AllowedCurrency String currency,
    @NotNull Boolean active
) {

}
