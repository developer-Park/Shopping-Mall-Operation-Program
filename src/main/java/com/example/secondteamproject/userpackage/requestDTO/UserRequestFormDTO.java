package com.example.secondteamproject.userpackage.requestDTO;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRequestFormDTO {

    private String title;
    private int item;
    private String content;

    private String sellername;
    private String category;
}
