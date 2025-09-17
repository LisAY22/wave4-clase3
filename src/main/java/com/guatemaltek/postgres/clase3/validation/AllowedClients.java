package com.guatemaltek.postgres.clase3.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedClientsValidator.class)

public @interface AllowedClients {

    String message() default "client not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
