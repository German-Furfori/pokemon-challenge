package com.truelayer.pokeapp.service;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.mapper.PokeMapper;
import com.truelayer.pokeapp.service.impl.PokeServiceImpl;
import com.truelayer.pokeapp.webclient.PokeApiWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_DESCRIPTION;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_HABITAT;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_IS_LEGENDARY;
import static com.truelayer.pokeapp.constant.DefaultValues.DEFAULT_POKEMON;
import static com.truelayer.pokeapp.util.TestUtils.getPokeApiResponse;
import static com.truelayer.pokeapp.util.TestUtils.getPokeInfoResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokeServiceTest {
    @Mock
    private PokeApiWebClient pokeApiWebClient;
    @Mock
    private PokeMapper pokeMapper;
    @InjectMocks
    private PokeServiceImpl pokeService;

    @Test
    void testFindPokemonInfo_ShouldReturnMappedResponse() {
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
}
