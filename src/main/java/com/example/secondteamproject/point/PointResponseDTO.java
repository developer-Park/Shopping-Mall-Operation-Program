package com.example.secondteamproject.point;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointResponseDTO {

    private String username;
    private Integer point;

    public PointResponseDTO(Integer point, String name) {
        this.point = point;
        this.username = name;
    }
}
