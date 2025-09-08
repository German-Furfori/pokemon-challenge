package com.truelayer.pokeapp.util;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.dto.poke.FlavorDto;
import com.truelayer.pokeapp.dto.poke.HabitatDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import java.util.List;

public class TestUtils {
    public static PokeApiResponseDto getPokeApiResponse(String flavor, String habitat, Boolean isLegendary) {
        FlavorDto flavorDto = new FlavorDto();
        flavorDto.setFlavorText(flavor);

        HabitatDto habitatDto = new HabitatDto();
        habitatDto.setName(habitat);

        PokeApiResponseDto pokeApiResponseDto = new PokeApiResponseDto();
        pokeApiResponseDto.setHabitat(habitatDto);
        pokeApiResponseDto.setFlavorTextEntries(List.of(flavorDto));
        pokeApiResponseDto.setIsLegendary(isLegendary);

        return pokeApiResponseDto;
    }

    public static PokeInfoResponseDto getPokeInfoResponse(String name, String flavor, String habitat, Boolean isLegendary) {
        PokeInfoResponseDto pokeInfoResponse = new PokeInfoResponseDto();
        pokeInfoResponse.setName(name);
        pokeInfoResponse.setDescription(flavor);
        pokeInfoResponse.setHabitat(habitat);
        pokeInfoResponse.setIsLegendary(isLegendary);

        return pokeInfoResponse;
    }
}
