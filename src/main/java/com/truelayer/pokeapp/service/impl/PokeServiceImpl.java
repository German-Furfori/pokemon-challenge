package com.truelayer.pokeapp.service.impl;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.service.PokeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokeServiceImpl implements PokeService {
    @Override
    public PokeInfoResponseDto findPokemonInfo(String name) {
        return new PokeInfoResponseDto();
    }
}