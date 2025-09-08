package com.truelayer.pokeapp.service;

import com.truelayer.pokeapp.dto.translation.TranslateResponseDto;

import java.util.Optional;

public interface TranslationService {
    Optional<TranslateResponseDto> translate(String text, String path);
}