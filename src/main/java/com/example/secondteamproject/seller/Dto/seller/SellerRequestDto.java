package com.example.secondteamproject.seller.Dto.seller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequestDto {
    private String description;

    private String img;
    private String nickname;


//    public SellerRequestDto(Seller seller) {
//        this.description =seller.getDescription();
//        this.img = seller.getImg();
//        this.nickname = seller.getNickname();
//    }
}
