package com.example.secondteamproject.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class ResolvedTokenAndAuthenticationDTO {
    private Authentication authenticationFreshToken;
    private Authentication authenticationAccessToken;

    public ResolvedTokenAndAuthenticationDTO(Authentication authenticationFreshToken, Authentication authenticationAccessToken) {
        this.authenticationFreshToken = authenticationFreshToken;
        this.authenticationAccessToken = authenticationAccessToken;
    }
}
