package com.truelayer.pokeapp.webclient;

import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PokeApiWebClient {
    @Value("${com.truelayer.pokeapp.poke-api-pokemon-info-path}")
    private String pokeApiPokemonInfoPath;

    private final WebClient webClientPokeApi;

    public PokeApiResponseDto getPokemonInfo(String name) {
        try {
            return webClientPokeApi
                    .get()
                    .uri(pokeApiPokemonInfoPath, name)
                    .retrieve()
                    .bodyToMono(PokeApiResponseDto.class)
                    .block();
        } catch (WebClientResponseException ex) {
            throw new RuntimeException();
        }
    }
}