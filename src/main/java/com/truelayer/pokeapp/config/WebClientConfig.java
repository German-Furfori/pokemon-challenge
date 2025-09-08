package com.truelayer.pokeapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class WebClientConfig {
    @Value("${com.truelayer.pokeapp.poke-api-uri}")
    private String pokeApiUri;
    @Value("${com.truelayer.pokeapp.translation-uri}")
    private String translationUri;

    @Bean(value = "webClientPokeApi")
    public WebClient webClientPokeApi() {
        return WebClient.builder()
                .baseUrl(pokeApiUri)
                .build();
    }

    @Bean(value = "webClientTranslation")
    public WebClient webClientTranslation() {
        return WebClient.builder()
                .baseUrl(translationUri)
                .build();
    }
}