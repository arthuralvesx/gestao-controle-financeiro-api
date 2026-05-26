package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.ApiErrorDto;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorDto> handleResponseStatus(ResponseStatusException exception) {
        return ResponseEntity
            .status(exception.getStatusCode())
            .body(new ApiErrorDto(exception.getReason()));
    }
}
