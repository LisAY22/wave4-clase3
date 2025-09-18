package com.guatemaltek.postgres.clase3.api.dto;


import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductResponse(Long id, String name, BigDecimal price, String currency,
                              Boolean active, OffsetDateTime createdAt, OffsetDateTime updatedAt) {

}
