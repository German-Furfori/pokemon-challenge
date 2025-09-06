package com.truelayer.pokeapp.dto.api;

import lombok.Data;

@Data
public class PokeInfoResponseDto {
    private String name;
    private String description;
    private String habitat;
    private Boolean isLegendary;
}
