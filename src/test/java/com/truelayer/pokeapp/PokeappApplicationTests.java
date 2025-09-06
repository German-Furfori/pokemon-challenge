package com.truelayer.pokeapp;

import com.truelayer.pokeapp.dto.poke.FlavorDto;
import com.truelayer.pokeapp.dto.poke.HabitatDto;
import com.truelayer.pokeapp.dto.poke.PokeApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
public abstract class PokeappApplicationTests {
	@Autowired
	protected MockMvc mockMvc;

	protected PokeApiResponseDto getPokeApiResponse() {
		FlavorDto flavor = new FlavorDto();
		flavor.setFlavorText("Pokemon description");

		HabitatDto habitat = new HabitatDto();
		habitat.setName("Habitat name");

		PokeApiResponseDto pokeApiResponseDto = new PokeApiResponseDto();
		pokeApiResponseDto.setHabitat(habitat);
		pokeApiResponseDto.setFlavorTextEntries(List.of(flavor));
		pokeApiResponseDto.setIsLegendary(false);

		return pokeApiResponseDto;
	}
}