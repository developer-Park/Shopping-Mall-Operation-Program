package com.example.secondteamproject.admin.dto;

import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.entity.User;

public class SellerListResponseDto {
    String sellerName;
    String nickname;
    String email;

    public SellerListResponseDto(Seller seller) {
        this.sellerName = seller.getSellerName();
        this.nickname = seller.getNickname();
        this.email = seller.getEmail();
    }


}
