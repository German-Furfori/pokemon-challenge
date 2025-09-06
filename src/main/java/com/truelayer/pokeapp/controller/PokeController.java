package com.truelayer.pokeapp.controller;

import com.truelayer.pokeapp.dto.api.PokeInfoResponseDto;
import com.truelayer.pokeapp.service.PokeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokeController {
    private final PokeService pokeService;

    @GetMapping(path = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public PokeInfoResponseDto findPokemonInfo(@PathVariable String name) {
        log.info("[PokeController] findPokemonInfo request: [{}]", name);
        PokeInfoResponseDto pokemonInfo = pokeService.findPokemonInfo(name);
        log.info("[PokeController] findPokemonInfo response: [{}]", pokemonInfo);
        return pokemonInfo;
    }
}