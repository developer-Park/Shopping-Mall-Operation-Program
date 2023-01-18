package com.example.secondteamproject.admin.dto;

import com.example.secondteamproject.entity.SellerRequest;
import com.example.secondteamproject.entity.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SellerRequestResponseDto {
    Long userId;
    String username;
    String title;
    String content;
    StatusEnum statusEnum;
    LocalDateTime createAt;


    public SellerRequestResponseDto(SellerRequest sellerRequest) {
        this.userId = sellerRequest.getUser().getId();
        this.username = sellerRequest.getUser().getUsername();
        this.title = sellerRequest.getTitle();
        this.content = sellerRequest.getContent();
        this.createAt = sellerRequest.getCreatedAt();
        this.statusEnum = sellerRequest.getStatusEnum();
    }

}
