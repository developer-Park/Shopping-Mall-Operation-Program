package com.example.secondteamproject.userpackage.responseDTO;

import com.example.secondteamproject.entity.ProductInquiry;
import com.example.secondteamproject.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDTO {
    private final String nickname;
    private Integer point;

    public UserResponseDTO(User user) {
        this.nickname = user.getNickname();
        this.point = user.getPoint();
    }
}
