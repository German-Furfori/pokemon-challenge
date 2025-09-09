package com.truelayer.pokeapp.service;

import com.truelayer.pokeapp.dto.translation.TranslateRequestDto;
import com.truelayer.pokeapp.dto.translation.TranslateResponseDto;
import com.truelayer.pokeapp.mapper.TranslationMapper;
import com.truelayer.pokeapp.service.impl.TranslationServiceImpl;
import com.truelayer.pokeapp.webclient.TranslationWebClient;
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
import static com.truelayer.pokeapp.constant.DefaultValues.SHAKESPEARE_PATH;
import static com.truelayer.pokeapp.util.TestUtils.getTranslateRequest;
import static com.truelayer.pokeapp.util.TestUtils.getTranslationResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TranslationServiceTest {
    @Mock
    private TranslationWebClient translationWebClient;
    @Mock
    private TranslationMapper translationMapper;
    @InjectMocks
    private TranslationServiceImpl translationService;

    private static Stream<Arguments> provideDataForTranslationTypes() {
        return Stream.of(
                Arguments.of(getTranslationResponse().get(), true),
                Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTranslationTypes")
    void translate_shouldReturnTranslateResponse(TranslateResponseDto translate, Boolean isPresent) {
        TranslateRequestDto translateRequest = getTranslateRequest(DEFAULT_DESCRIPTION);
        Optional<TranslateResponseDto> translateResponse = Optional.ofNullable(translate);

        when(translationMapper.textToTranslateRequest(DEFAULT_DESCRIPTION))
                .thenReturn(translateRequest);
        when(translationWebClient.translate(translateRequest, SHAKESPEARE_PATH))
                .thenReturn(translateResponse);

        Optional<TranslateResponseDto> result = translationService.translate(DEFAULT_DESCRIPTION, SHAKESPEARE_PATH);

        assertEquals(isPresent, result.isPresent());
        assertEquals(translateResponse, result);

        verify(translationMapper, times(1)).textToTranslateRequest(DEFAULT_DESCRIPTION);
        verify(translationWebClient, times(1)).translate(translateRequest, SHAKESPEARE_PATH);
    }
}
