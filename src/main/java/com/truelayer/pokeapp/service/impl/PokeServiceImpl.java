package com.truelayer.pokeapp.service.impl;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.mapper.PokeMapper;
import com.truelayer.pokeapp.service.PokeApiService;
import com.truelayer.pokeapp.service.PokeService;
import com.truelayer.pokeapp.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.truelayer.pokeapp.constant.TranslationTypes.CAVE_POKEMON_HABITAT;
import static com.truelayer.pokeapp.constant.TranslationTypes.SHAKESPEARE_TRANSLATE_PATH;
import static com.truelayer.pokeapp.constant.TranslationTypes.YODA_TRANSLATE_PATH;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokeServiceImpl implements PokeService {
    private final PokeApiService pokeApiService;
    private final PokeMapper pokeMapper;
    private final TranslationService translationService;

    @Override
    public PokeInfoResponseDto findPokemonInfo(String name) {
        PokeApiResponseDto pokeApiResponse = pokeApiService.getPokemonInfo(name);
        return pokeMapper.fromPokeApiResponseToPokeInfoResponse(name, pokeApiResponse);
    }

    @Override
    public PokeInfoResponseDto findPokemonTranslatedInfo(String name) {
        PokeInfoResponseDto pokeInfoResponse = this.findPokemonInfo(name);
        String translationType = this.getTranslationType(pokeInfoResponse);
        translationService.translate(pokeInfoResponse.getDescription(), translationType)
                .ifPresent(response -> pokeMapper.updatePokemonDescription(response, pokeInfoResponse));
        return pokeInfoResponse;
    }

    private String getTranslationType(PokeInfoResponseDto pokemon) {
        if (CAVE_POKEMON_HABITAT.equals(pokemon.getHabitat())
                || pokemon.getIsLegendary()) return YODA_TRANSLATE_PATH;
        return SHAKESPEARE_TRANSLATE_PATH;
    }
}