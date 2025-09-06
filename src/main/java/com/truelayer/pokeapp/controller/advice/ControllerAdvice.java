package com.truelayer.pokeapp.controller.advice;

import com.truelayer.pokeapp.dto.error.ErrorResponseDto;
import com.truelayer.pokeapp.exception.PokeApiGenericException;
import com.truelayer.pokeapp.exception.PokeApiNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {
    @ExceptionHandler(PokeApiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto pokeApiNotFoundExceptionHandler(PokeApiNotFoundException exception) {
        log.error("[ControllerAdvice] pokeApiNotFoundExceptionHandler, exception: [{}]", exception.getMessage());
        return this.generateError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PokeApiGenericException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto pokeApiGenericExceptionHandler(PokeApiGenericException exception) {
        log.error("[ControllerAdvice] pokeApiGenericExceptionHandler, exception: [{}]", exception.getMessage());
        return this.generateError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ErrorResponseDto generateError(HttpStatusCode status, String description) {
        return new ErrorResponseDto(status.toString(), description);
    }
}