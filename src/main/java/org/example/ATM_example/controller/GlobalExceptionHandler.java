package org.example.ATM_example.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.example.ATM_example.exception.ErrorResponse;
import org.example.ATM_example.exception.WalletNotFoundException;
import org.example.ATM_example.exception.WithdrawOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleWalletNotFoundException(WalletNotFoundException exception) {
        log.error("Wallet not found: {}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(WithdrawOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleWithdrawOperationException(WithdrawOperationException exception) {
        log.warn("Withdraw operation failed: {}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnsupportedOperationException(UnsupportedOperationException exception) {
        log.error("Unsupported operation: {}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getConstraintViolations()
                .forEach(constraintViolation -> constraintViolation.getPropertyPath()
                .forEach(error -> errors.put(constraintViolation.getMessage(), error.getName())));
        log.error(errors.toString());
        return new ErrorResponse(errors.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(ValidationException exception) {
        log.error("Validation exception: {}", exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        Throwable rootCause = exception.getRootCause();
        if (rootCause instanceof InvalidFormatException invalidEx) {
            if (invalidEx.getTargetType() != null && invalidEx.getTargetType().isEnum()) {
                Class<Enum<?>> enumType = (Class<Enum<?>>) invalidEx.getTargetType();
                String allowedValues = Arrays.stream(enumType.getEnumConstants())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));
                String message = String.format(
                        "Invalid value '%s' for field '%s'. Allowed values: %s",
                        invalidEx.getValue(),
                        invalidEx.getPath().get(0).getFieldName(),
                        allowedValues
                );
                log.warn(message);
                return new ErrorResponse(message);
            }
        }
        log.error("JSON parse error: {}", exception.getMessage());
        return new ErrorResponse("Invalid request format. Check your request");
    }
}
