package com.truelayer.pokeapp.webclient;

import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.exception.PokeApiGenericException;
import com.truelayer.pokeapp.exception.PokeApiNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static com.truelayer.pokeapp.constant.ErrorMessages.NON_EXISTING_POKEMON;

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
                    .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                            Mono.error(new PokeApiNotFoundException(String.format(NON_EXISTING_POKEMON, name)))
                    )
                    .bodyToMono(PokeApiResponseDto.class)
                    .block();
        } catch (WebClientResponseException ex) {
            throw new PokeApiGenericException(ex.getMessage(), ex.getStatusCode());
        }
    }
}