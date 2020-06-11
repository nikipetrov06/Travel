package com.java.travel.controllers.handlers;

import com.java.travel.dtos.ErrorDto;
import com.java.travel.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequests(BadRequestException exception) {
        log.error("User has given invalid information", exception);
        return convertExceptionToErrorDto(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final Map<String, String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
        log.error("User didn't pass the validations", exception);
        return convertExceptionToErrorDto(HttpStatus.BAD_REQUEST, errorMessages.toString());
    }

    private static ErrorDto convertExceptionToErrorDto(final HttpStatus status, final String message) {
        return new ErrorDto(message, status, LocalDateTime.now());
    }
}
