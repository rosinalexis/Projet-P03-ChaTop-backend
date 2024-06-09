package com.openclassrooms.chatpo.dto;

import com.openclassrooms.chatpo.models.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class TokenDto {


    private String token;

    public static TokenDto fromEntity(Token token) {

        return TokenDto.builder()
                .token(token.getToken())
                .build();
    }


    public static Token toEntity(TokenDto tokenDto) {
        return Token.builder()
                .token(tokenDto.getToken())
                .build();
    }
}
