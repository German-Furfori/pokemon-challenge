package com.truelayer.pokeapp.service.impl;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.dto.translation.TranslateRequestDto;
import com.truelayer.pokeapp.dto.translation.TranslateResponseDto;
import com.truelayer.pokeapp.mapper.PokeMapper;
import com.truelayer.pokeapp.mapper.TranslationMapper;
import com.truelayer.pokeapp.service.PokeService;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import com.truelayer.pokeapp.webclient.TranslationWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static com.truelayer.pokeapp.constant.TranslationTypes.CAVE_POKEMON_HABITAT;
import static com.truelayer.pokeapp.constant.TranslationTypes.SHAKESPEARE_TRANSLATE_PATH;
import static com.truelayer.pokeapp.constant.TranslationTypes.YODA_TRANSLATE_PATH;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokeServiceImpl implements PokeService {
    private final PokeApiWebClient pokeApiWebClient;
    private final TranslationWebClient translationWebClient;
    private final PokeMapper pokeMapper;
    private final TranslationMapper translateMapper;

    @Override
    public PokeInfoResponseDto findPokemonInfo(String name) {
        PokeApiResponseDto pokeApiResponse = pokeApiWebClient.getPokemonInfo(name);
        return pokeMapper.fromPokeApiResponseToPokeInfoResponse(name, pokeApiResponse);
    }

    @Override
    public PokeInfoResponseDto findPokemonTranslatedInfo(String name) {
        PokeInfoResponseDto pokeInfoResponse = this.findPokemonInfo(name);
        this.translateDescription(pokeInfoResponse)
                        .ifPresent(response -> pokeMapper.updatePokemonDescription(response, pokeInfoResponse));
        return pokeInfoResponse;
    }

    private Optional<TranslateResponseDto> translateDescription(PokeInfoResponseDto pokeInfoResponse) {
        TranslateRequestDto translateRequest = translateMapper.pokeInfoResponseToTranslateRequest(pokeInfoResponse);
        String translateType = this.getTranslationType(pokeInfoResponse);
        return translationWebClient.translate(translateRequest, translateType);
    }

    private String getTranslationType(PokeInfoResponseDto pokemon) {
        if (CAVE_POKEMON_HABITAT.equals(pokemon.getHabitat())
                || pokemon.getIsLegendary()) return YODA_TRANSLATE_PATH;
        return SHAKESPEARE_TRANSLATE_PATH;
    }
}