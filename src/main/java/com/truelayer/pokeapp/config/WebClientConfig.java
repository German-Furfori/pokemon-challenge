package com.truelayer.pokeapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${com.truelayer.pokeapp.poke-api-uri}")
    private String pokeApiUri;

    @Bean(value = "webClientPokeApi")
    public WebClient webClientPokeApi() {
        return WebClient.builder()
                .baseUrl(pokeApiUri)
                .build();
    }
}