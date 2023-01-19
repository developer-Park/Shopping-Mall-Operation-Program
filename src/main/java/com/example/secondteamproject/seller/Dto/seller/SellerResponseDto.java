package com.example.secondteamproject.seller.Dto.seller;

import com.example.secondteamproject.seller.entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponseDto {
    private String description;

    private String img;
    private String nickname;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

//    public SellerResponseDto(String description, String img, String nickname) {
//        this.description = description;
//        this.img = img;
//        this.nickname = nickname;
//    }
    public SellerResponseDto(Seller seller) {
        this.description =seller.getDescription();
        this.img = seller.getImg();
        this.nickname = seller.getNickname();
        this.modifiedAt =seller.getModifiedAt();
        this.createdAt = seller.getCreatedAt();
    }
}
