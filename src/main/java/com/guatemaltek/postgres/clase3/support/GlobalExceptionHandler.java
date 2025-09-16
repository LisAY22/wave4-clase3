package com.guatemaltek.postgres.clase3.support;

import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiError handleNotFound(NotFoundException ex,
      org.springframework.web.context.request.WebRequest req) {
    return new ApiError(OffsetDateTime.now(), 404, "Not Found", ex.getMessage(),
        req.getDescription(false), Map.of());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleValidation(MethodArgumentNotValidException ex,
      org.springframework.web.context.request.WebRequest req) {
    var details = new LinkedHashMap<String, Object>();
    ex.getBindingResult().getFieldErrors()
        .forEach(fe -> details.put(fe.getField(), fe.getDefaultMessage()));
    return new ApiError(OffsetDateTime.now(), 400, "Bad Request", "Validation failed",
        req.getDescription(false), details);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleConstraintViolations(ConstraintViolationException ex,
      org.springframework.web.context.request.WebRequest req) {
    var details = new LinkedHashMap<String, Object>();
    ex.getConstraintViolations()
        .forEach(v -> details.put(v.getPropertyPath().toString(), v.getMessage()));
    return new ApiError(OffsetDateTime.now(), 400, "Bad Request", "Constraint violation",
        req.getDescription(false), details);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleIllegalArg(IllegalArgumentException ex,
      org.springframework.web.context.request.WebRequest req) {
    return new ApiError(OffsetDateTime.now(), 400, "Bad Request", ex.getMessage(),
        req.getDescription(false), Map.of());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiError handleOther(Exception ex,
      org.springframework.web.context.request.WebRequest req) {
    return new ApiError(OffsetDateTime.now(), 500, "Internal Server Error", "Unexpected error",
        req.getDescription(false), Map.of("cause", ex.getClass().getSimpleName()));
  }

  record ApiError(OffsetDateTime timestamp, int status, String error, String message, String path,
                  Map<String, Object> details) {

  }
}
