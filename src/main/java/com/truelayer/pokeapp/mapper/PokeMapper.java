package com.truelayer.pokeapp.mapper;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.poke.FlavorDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import com.truelayer.pokeapp.dto.translation.TranslateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.List;
import java.util.Objects;

import static com.truelayer.pokeapp.constant.ErrorMessages.NO_DESCRIPTION_FOUND;

@Mapper(componentModel = "spring")
public interface PokeMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "dto.flavorTextEntries", qualifiedByName = "mapDescription")
    @Mapping(target = "habitat", source = "dto.habitat.name")
    @Mapping(target = "isLegendary", source = "dto.isLegendary")
    PokeInfoResponseDto fromPokeApiResponseToPokeInfoResponse(String name, PokeApiResponseDto dto);

    @Named(value = "mapDescription")
    default String mapDescription(List<FlavorDto> flavorTextEntries) {
        return flavorTextEntries.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(FlavorDto::getFlavorText)
                .map(text -> text.replaceAll("[\\n\\f]+", " "))
                .orElse(NO_DESCRIPTION_FOUND);
    }

    @Mapping(target = "description", source = "contents.translated")
    void updatePokemonDescription(TranslateResponseDto translateResponse,
                                                    @MappingTarget PokeInfoResponseDto pokeInfoResponse);
}
