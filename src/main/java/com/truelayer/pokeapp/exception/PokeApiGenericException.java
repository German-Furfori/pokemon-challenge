package com.truelayer.pokeapp.exception;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class PokeApiGenericException extends RuntimeException {
    private HttpStatusCode status;

    public PokeApiGenericException(String message, HttpStatusCode status) {
        super("Generic Error: " + message);
        this.status = status;
    }
}