package com.truelayer.pokeapp.mapper;

import com.truelayer.pokeapp.dto.translation.TranslateRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslationMapper {
    TranslateRequestDto textToTranslateRequest(String text);
}