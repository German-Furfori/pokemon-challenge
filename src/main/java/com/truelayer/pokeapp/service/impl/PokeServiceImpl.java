package com.truelayer.pokeapp.service.impl;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.mapper.PokeMapper;
import com.truelayer.pokeapp.service.PokeService;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokeServiceImpl implements PokeService {
    private final PokeApiWebClient pokeApiWebClient;
    private final PokeMapper pokeMapper;

    @Override
    public PokeInfoResponseDto findPokemonInfo(String name) {
        PokeApiResponseDto pokeApiResponse = pokeApiWebClient.getPokemonInfo(name);
        return pokeMapper.fromPokeApiResponseToPokeInfoResponse(name, pokeApiResponse);
    }

    @Override
    public PokeInfoResponseDto findPokemonTranslatedInfo(String name) {
        return this.findPokemonInfo(name);
    }
}