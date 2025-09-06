package com.truelayer.pokeapp.exception;

public class PokeApiGenericException extends RuntimeException {
    public PokeApiGenericException(String message) {
        super("Internal Error: " + message);
    }
}