package com.truelayer.pokeapp.exception;

public class PokeApiNotFoundException extends RuntimeException {
    public PokeApiNotFoundException(String message) {
        super(message);
    }
}