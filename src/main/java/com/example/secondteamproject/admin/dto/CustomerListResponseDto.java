package com.example.secondteamproject.admin.dto;

import com.example.secondteamproject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerListResponseDto {
    Long userId;
    String username;
    String nickname;
    String email;

    public CustomerListResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
