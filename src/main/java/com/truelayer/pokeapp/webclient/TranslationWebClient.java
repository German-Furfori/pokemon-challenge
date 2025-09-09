package com.truelayer.pokeapp.webclient;

import com.truelayer.pokeapp.dto.translation.TranslateRequestDto;
import com.truelayer.pokeapp.dto.translation.TranslateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TranslationWebClient {
    @Value("${com.truelayer.pokeapp.translation-path}")
    private String translationPath;

    private final WebClient webClientTranslation;

    public Optional<TranslateResponseDto> translate(TranslateRequestDto translateRequest, String translateType) {
        try {
            return Optional.ofNullable(webClientTranslation
                    .post()
                    .uri(translationPath, translateType)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(translateRequest)
                    .retrieve()
                    .bodyToMono(TranslateResponseDto.class)
                    .block());
        } catch (WebClientException ex) {
            log.error("[TranslationWebClient] translate, exception: [{}]", ex.getMessage());
            return Optional.empty();
        }
    }
}