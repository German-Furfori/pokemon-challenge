package com.truelayer.pokeapp.mapper;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.translation.TranslateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TranslationMapper {
    @Mapping(target = "text", source = "description")
    TranslateRequestDto pokeInfoResponseToTranslateRequest(PokeInfoResponseDto pokeInfoResponse);
}