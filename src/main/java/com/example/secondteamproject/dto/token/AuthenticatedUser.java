package com.example.secondteamproject.dto.token;

import com.example.secondteamproject.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class AuthenticatedUser {
    private final UserRoleEnum userRoleEnum;
    private final String username;

    public AuthenticatedUser(UserRoleEnum userRoleEnum, String username) {
        this.userRoleEnum = userRoleEnum;
        this.username = username;
    }
}
