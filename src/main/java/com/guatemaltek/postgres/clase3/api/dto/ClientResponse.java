package com.guatemaltek.postgres.clase3.api.dto;

import java.time.OffsetDateTime;

public record ClientResponse(Long id, String name, Integer age, String address, String phone,
                             Boolean active, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
