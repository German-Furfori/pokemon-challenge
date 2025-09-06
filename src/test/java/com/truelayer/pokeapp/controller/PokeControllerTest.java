package com.truelayer.pokeapp.controller;

import com.truelayer.pokeapp.PokeappApplicationTests;
import com.truelayer.pokeapp.exception.PokeApiGenericException;
import com.truelayer.pokeapp.exception.PokeApiNotFoundException;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PokeControllerTest extends PokeappApplicationTests {
    @MockitoBean
    private PokeApiWebClient pokeApiWebClient;
    private final String pathPokemon = "/pokemon/{name}";
    private static final String DEFAULT_POKEMON = "pikachu";

    @Test
    @SneakyThrows
    void findPokemonInfo_withCorrectFields_returnPokemonInfo() {
        ArgumentCaptor<String> argumentCaptorName = ArgumentCaptor.forClass(String.class);

        given(pokeApiWebClient.getPokemonInfo(argumentCaptorName.capture()))
                .willReturn(this.getPokeApiResponse());

        mockMvc.perform(get(pathPokemon, DEFAULT_POKEMON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.habitat").exists())
                .andExpect(jsonPath("$.isLegendary").exists())
                .andExpect(jsonPath("$.name").value("pikachu"))
                .andExpect(jsonPath("$.description").value("Pokemon description"))
                .andExpect(jsonPath("$.habitat").value("Habitat name"))
                .andExpect(jsonPath("$.isLegendary").value(false));

        assertEquals("pikachu", argumentCaptorName.getValue());

        verify(pokeApiWebClient, times(1)).getPokemonInfo(argumentCaptorName.getValue());
    }

    @Test
    @SneakyThrows
    void findPokemonInfo_withCorrectFields_returnNotFound() {
        given(pokeApiWebClient.getPokemonInfo(any(String.class)))
                .willThrow(new PokeApiNotFoundException("Pokemon Not Found"));

        mockMvc.perform(get(pathPokemon, DEFAULT_POKEMON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.code").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.description").value("Pokemon Not Found"));

        verify(pokeApiWebClient, times(1)).getPokemonInfo(any(String.class));
    }

    @Test
    @SneakyThrows
    void findPokemonInfo_withCorrectFields_returnInternalError() {
        given(pokeApiWebClient.getPokemonInfo(any(String.class)))
                .willThrow(new PokeApiGenericException("Error"));

        mockMvc.perform(get(pathPokemon, DEFAULT_POKEMON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.code").value("500 INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.description").value("Internal Error: Error"));

        verify(pokeApiWebClient, times(1)).getPokemonInfo(any(String.class));
    }
}