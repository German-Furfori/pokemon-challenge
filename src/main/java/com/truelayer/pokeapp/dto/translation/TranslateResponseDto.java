package com.truelayer.pokeapp.dto.translation;

import lombok.Data;

@Data
public class TranslateResponseDto {
    public TranslateResponseDto(String text) {
        ContentsDto contents = new ContentsDto();
        contents.setTranslated(text);
        this.contents = contents;
    }

    private ContentsDto contents;
}