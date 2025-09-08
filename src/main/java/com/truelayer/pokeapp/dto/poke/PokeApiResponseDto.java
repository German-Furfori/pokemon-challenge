package com.truelayer.pokeapp.dto.poke;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class PokeApiResponseDto {
    @JsonProperty("flavor_text_entries")
    private List<FlavorDto> flavorTextEntries;
    private HabitatDto habitat;
    @JsonProperty("is_legendary")
    private Boolean isLegendary;
}
