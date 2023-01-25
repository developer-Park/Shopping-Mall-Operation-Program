package com.example.secondteamproject.dto.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;

    public TokenResponseDto(String accessToken,  String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
