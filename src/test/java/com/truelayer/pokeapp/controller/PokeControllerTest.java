package com.truelayer.pokeapp.controller;

import com.truelayer.pokeapp.PokeappApplicationTests;
import com.truelayer.pokeapp.dto.translation.TranslateRequestDto;
import com.truelayer.pokeapp.exception.PokeApiGenericException;
import com.truelayer.pokeapp.exception.PokeApiNotFoundException;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import com.truelayer.pokeapp.webclient.TranslationWebClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.Optional;
import java.util.stream.Stream;

import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_DESCRIPTION;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_HABITAT;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_IS_LEGENDARY;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_POKEMON;
import static com.truelayer.pokeapp.util.TestUtils.getPokeApiResponse;
import static com.truelayer.pokeapp.util.TestUtils.getTranslationResponse;
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
    @MockitoBean
    private TranslationWebClient translationWebClient;
    private final String pathPokemon = "/pokemon/{name}";
    private final String pathTranslatedPokemon = "/pokemon/translated/{name}";

    private static Stream<Arguments> provideDataForTranslationTypes() {
        return Stream.of(
                Arguments.of("shakespeare", "habitat", false),
                Arguments.of("yoda", "cave", false),
                Arguments.of("yoda", "habitat", true)
        );
    }

    @Test
    @SneakyThrows
    void findPokemonInfo_withCorrectFields_returnPokemonInfo() {
        ArgumentCaptor<String> argumentCaptorName = ArgumentCaptor.forClass(String.class);

        given(pokeApiWebClient.getPokemonInfo(argumentCaptorName.capture()))
                .willReturn(getPokeApiResponse(DEFAULT_DESCRIPTION, DEFAULT_HABITAT, DEFAULT_IS_LEGENDARY));

        mockMvc.perform(get(pathPokemon, DEFAULT_POKEMON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.habitat").exists())
                .andExpect(jsonPath("$.isLegendary").exists())
                .andExpect(jsonPath("$.name").value("pikachu"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.habitat").value("habitat"))
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

    @Test
    @SneakyThrows
    void findPokemonInfo_withCorrectFields_returnPokemonInfoWithDefaultDescription() {
        given(pokeApiWebClient.getPokemonInfo(any(String.class)))
                .willReturn(getPokeApiResponse(null, DEFAULT_HABITAT, DEFAULT_IS_LEGENDARY));

        mockMvc.perform(get(pathPokemon, DEFAULT_POKEMON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.habitat").exists())
                .andExpect(jsonPath("$.isLegendary").exists())
                .andExpect(jsonPath("$.name").value("pikachu"))
                .andExpect(jsonPath("$.description").value("No description found"))
                .andExpect(jsonPath("$.habitat").value("habitat"))
                .andExpect(jsonPath("$.isLegendary").value(false));

        verify(pokeApiWebClient, times(1)).getPokemonInfo(any(String.class));
    }

    @Test
    @SneakyThrows
    void findPokemonTranslatedInfo_withCorrectFields_returnPokemonTranslatedInfo() {
        ArgumentCaptor<String> argumentCaptorName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argumentCaptorTranslationType = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<TranslateRequestDto> argumentCaptorTranslate = ArgumentCaptor.forClass(TranslateRequestDto.class);

        given(pokeApiWebClient.getPokemonInfo(argumentCaptorName.capture()))
                .willReturn(getPokeApiResponse(DEFAULT_DESCRIPTION, DEFAULT_HABITAT, DEFAULT_IS_LEGENDARY));
        given(translationWebClient.translate(argumentCaptorTranslate.capture(), argumentCaptorTranslationType.capture()))
                .willReturn(getTranslationResponse());

        mockMvc.perform(get(pathTranslatedPokemon, DEFAULT_POKEMON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.habitat").exists())
                .andExpect(jsonPath("$.isLegendary").exists())
                .andExpect(jsonPath("$.name").value("pikachu"))
                .andExpect(jsonPath("$.description").value("Translated description"))
                .andExpect(jsonPath("$.habitat").value("habitat"))
                .andExpect(jsonPath("$.isLegendary").value(false));

        assertEquals("pikachu", argumentCaptorName.getValue());

        assertEquals("shakespeare", argumentCaptorTranslationType.getValue());

        assertEquals("description", argumentCaptorTranslate.getValue().getText());

        verify(pokeApiWebClient, times(1)).getPokemonInfo(argumentCaptorName.getValue());
        verify(translationWebClient, times(1)).translate(argumentCaptorTranslate.getValue(),
                argumentCaptorTranslationType.getValue());
    }

    @ParameterizedTest
    @MethodSource("provideDataForTranslationTypes")
    @SneakyThrows
    void findPokemonTranslatedInfo_withSeveralPokemonTypes_verifyTranslationTypes(String translationType, String habitat, Boolean isLegendary) {
        ArgumentCaptor<String> argumentCaptorTranslationType = ArgumentCaptor.forClass(String.class);

        given(pokeApiWebClient.getPokemonInfo(any(String.class)))
                .willReturn(getPokeApiResponse(DEFAULT_DESCRIPTION, habitat, isLegendary));
        given(translationWebClient.translate(any(TranslateRequestDto.class), argumentCaptorTranslationType.capture()))
                .willReturn(getTranslationResponse());

        mockMvc.perform(get(pathTranslatedPokemon, DEFAULT_POKEMON));

        assertEquals(translationType, argumentCaptorTranslationType.getValue());
    }

    @Test
    @SneakyThrows
    void findPokemonTranslatedInfo_withNoTranslateResponse_returnPokemonStandardInfo() {
        given(pokeApiWebClient.getPokemonInfo(any(String.class)))
                .willReturn(getPokeApiResponse(DEFAULT_DESCRIPTION, DEFAULT_HABITAT, DEFAULT_IS_LEGENDARY));
        given(translationWebClient.translate(any(TranslateRequestDto.class), any(String.class)))
                .willReturn(Optional.empty());

        mockMvc.perform(get(pathTranslatedPokemon, DEFAULT_POKEMON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description").value("description"));
    }
}