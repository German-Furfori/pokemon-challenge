package com.truelayer.pokeapp.service.impl;

import com.truelayer.pokeapp.dto.translation.TranslateRequestDto;
import com.truelayer.pokeapp.dto.translation.TranslateResponseDto;
import com.truelayer.pokeapp.mapper.TranslationMapper;
import com.truelayer.pokeapp.service.TranslationService;
import com.truelayer.pokeapp.webclient.TranslationWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final TranslationWebClient translationWebClient;
    private final TranslationMapper translateMapper;

    @Override
    public Optional<TranslateResponseDto> translate(String text, String path) {
        TranslateRequestDto translateRequest = translateMapper.textToTranslateRequest(text);
        return translationWebClient.translate(translateRequest, path);
    }
}