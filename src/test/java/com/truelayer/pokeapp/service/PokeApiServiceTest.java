package com.truelayer.pokeapp.service;

import com.truelayer.pokeapp.PokeappApplicationTests;
import com.truelayer.pokeapp.service.impl.PokeApiServiceImpl;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_POKEMON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PokeApiServiceTest extends PokeappApplicationTests {
    @MockitoBean
    private PokeApiWebClient pokeApiWebClient;
    @Autowired
    private PokeApiServiceImpl pokeApiService;

    @Test
    void getPokemonInfo_withSeveralCalls_verifyCache() {
        when(pokeApiWebClient.getPokemonInfo(DEFAULT_POKEMON)).thenReturn(any());

        pokeApiService.getPokemonInfo(DEFAULT_POKEMON);
        pokeApiService.getPokemonInfo(DEFAULT_POKEMON);
        pokeApiService.getPokemonInfo("bulbasaur");
        pokeApiService.getPokemonInfo("bulbasaur");

        verify(pokeApiWebClient, times(2)).getPokemonInfo(any());
    }
}