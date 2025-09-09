package com.truelayer.pokeapp.service.impl;

import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.service.PokeApiService;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokeApiServiceImpl implements PokeApiService {
    private final PokeApiWebClient pokeApiWebClient;

    @Override
    @Cacheable(value = "pokemon")
    public PokeApiResponseDto getPokemonInfo(String name) {
        return pokeApiWebClient.getPokemonInfo(name);
    }
}