package com.truelayer.pokeapp.dto.poke;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlavorDto {
    @JsonProperty("flavor_text")
    private String flavorText;
}
