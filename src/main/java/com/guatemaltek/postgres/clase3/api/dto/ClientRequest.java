package com.guatemaltek.postgres.clase3.api.dto;

import jakarta.validation.constraints.*;

public record ClientRequest(@NotBlank @Size(max = 60) String name,
                            @NotNull @Min(0) @Max(100) Integer age,
                            @NotBlank String address,
                            @NotBlank String phone,
                            @NotNull Boolean active) {
}
