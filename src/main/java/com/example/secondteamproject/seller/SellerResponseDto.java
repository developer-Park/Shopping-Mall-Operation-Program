package com.example.secondteamproject.seller;

import com.example.secondteamproject.entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponseDto {
    private String description;

    private String nickname;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public SellerResponseDto(Seller seller) {
        this.description =seller.getDescription();
        this.nickname = seller.getNickname();
        this.modifiedAt =seller.getModifiedAt();
        this.createdAt = seller.getCreatedAt();
    }
}