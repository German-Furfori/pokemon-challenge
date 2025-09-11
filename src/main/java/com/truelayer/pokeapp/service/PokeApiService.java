package com.truelayer.pokeapp.service;

import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;

public interface PokeApiService {
    PokeApiResponseDto getPokemonInfo(String name);
}