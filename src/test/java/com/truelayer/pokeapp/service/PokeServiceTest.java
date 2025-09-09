package com.truelayer.pokeapp.service;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.dto.translation.TranslateResponseDto;
import com.truelayer.pokeapp.exception.PokeApiGenericException;
import com.truelayer.pokeapp.exception.PokeApiNotFoundException;
import com.truelayer.pokeapp.mapper.PokeMapper;
import com.truelayer.pokeapp.service.impl.PokeServiceImpl;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.stream.Stream;

import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_DESCRIPTION;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_HABITAT;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_IS_LEGENDARY;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_POKEMON;
import static com.truelayer.pokeapp.constant.DefaultValues.SHAKESPEARE_PATH;
import static com.truelayer.pokeapp.constant.DefaultValues.YODA_PATH;
import static com.truelayer.pokeapp.util.TestUtils.getPokeApiResponse;
import static com.truelayer.pokeapp.util.TestUtils.getPokeInfoResponse;
import static com.truelayer.pokeapp.util.TestUtils.getTranslationResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokeServiceTest {
    @Mock
    private PokeApiWebClient pokeApiWebClient;
    @Mock
    private PokeMapper pokeMapper;
    @Mock
    private TranslationService translationService;
    @InjectMocks
    private PokeServiceImpl pokeService;

    private static Stream<Arguments> provideDataForTranslationTypes() {
        return Stream.of(
                Arguments.of(SHAKESPEARE_PATH, DEFAULT_HABITAT, DEFAULT_IS_LEGENDARY, getTranslationResponse().get(), 1),
                Arguments.of(YODA_PATH, "cave", DEFAULT_IS_LEGENDARY, getTranslationResponse().get(), 1),
                Arguments.of(YODA_PATH, DEFAULT_HABITAT, true, getTranslationResponse().get(), 1),
                Arguments.of(YODA_PATH, DEFAULT_HABITAT, true, null, 0)
        );
    }

    @Test
    void findPokemonInfo_shouldReturnMappedResponse() {
        PokeApiResponseDto mockApiResponse = getPokeApiResponse(DEFAULT_DESCRIPTION, DEFAULT_HABITAT, DEFAULT_IS_LEGENDARY);
        PokeInfoResponseDto expectedResponse = getPokeInfoResponse(DEFAULT_POKEMON, DEFAULT_DESCRIPTION, DEFAULT_HABITAT, DEFAULT_IS_LEGENDARY);

        when(pokeApiWebClient.getPokemonInfo(DEFAULT_POKEMON)).thenReturn(mockApiResponse);
        when(pokeMapper.fromPokeApiResponseToPokeInfoResponse(DEFAULT_POKEMON, mockApiResponse))
                .thenReturn(expectedResponse);

        PokeInfoResponseDto actualResponse = pokeService.findPokemonInfo(DEFAULT_POKEMON);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(pokeApiWebClient, times(1)).getPokemonInfo(DEFAULT_POKEMON);
        verify(pokeMapper, times(1)).fromPokeApiResponseToPokeInfoResponse(DEFAULT_POKEMON, mockApiResponse);
    }

    @Test
    void findPokemonInfo_notFoundExceptionFromPokeApi() {
        when(pokeApiWebClient.getPokemonInfo(DEFAULT_POKEMON))
                .thenThrow(new PokeApiNotFoundException("Not Found"));

        PokeApiNotFoundException ex = assertThrows(PokeApiNotFoundException.class, () -> pokeService.findPokemonInfo(DEFAULT_POKEMON));

        assertEquals("Not Found", ex.getMessage());

        verify(pokeApiWebClient, times(1)).getPokemonInfo(DEFAULT_POKEMON);
        verify(pokeMapper, never()).fromPokeApiResponseToPokeInfoResponse(any(), any());
    }

    @Test
    void findPokemonInfo_genericExceptionFromPokeApi() {
        when(pokeApiWebClient.getPokemonInfo(DEFAULT_POKEMON))
                .thenThrow(new PokeApiGenericException("Error"));

        PokeApiGenericException ex = assertThrows(PokeApiGenericException.class, () -> pokeService.findPokemonInfo(DEFAULT_POKEMON));

        assertEquals("Internal Error: Error", ex.getMessage());

        verify(pokeApiWebClient, times(1)).getPokemonInfo(DEFAULT_POKEMON);
        verify(pokeMapper, never()).fromPokeApiResponseToPokeInfoResponse(any(), any());
    }

    @ParameterizedTest
    @MethodSource("provideDataForTranslationTypes")
    void findPokemonTranslatedInfo_shouldUpdateDescriptionWhenTranslationIsPresent(String translationType,
                                                                                   String habitat,
                                                                                   Boolean isLegendary,
                                                                                   TranslateResponseDto translate,
                                                                                   Integer mapperTimesExpected) {
        PokeApiResponseDto mockApiResponse = getPokeApiResponse(DEFAULT_DESCRIPTION, habitat, isLegendary);
        PokeInfoResponseDto pokeInfo = getPokeInfoResponse(DEFAULT_POKEMON, DEFAULT_DESCRIPTION, habitat, isLegendary);
        Optional<TranslateResponseDto> translateResponse = Optional.ofNullable(translate);

        when(pokeApiWebClient.getPokemonInfo(DEFAULT_POKEMON))
                .thenReturn(mockApiResponse);
        when(pokeMapper.fromPokeApiResponseToPokeInfoResponse(DEFAULT_POKEMON, mockApiResponse))
                .thenReturn(pokeInfo);
        when(translationService.translate(DEFAULT_DESCRIPTION, translationType))
                .thenReturn(translateResponse);

        pokeService.findPokemonTranslatedInfo(DEFAULT_POKEMON);

        verify(translationService, times(1)).translate(DEFAULT_DESCRIPTION, translationType);
        verify(pokeMapper, times(mapperTimesExpected)).updatePokemonDescription(translate, pokeInfo);
    }
}
