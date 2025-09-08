package com.truelayer.pokeapp.service;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;

public interface PokeService {
    PokeInfoResponseDto findPokemonInfo(String name);
}