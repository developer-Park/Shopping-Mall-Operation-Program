package com.example.secondteamproject.userpackage.responseDTO;


import com.example.secondteamproject.entity.Seller;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OneSellerResponseDTO {
    private String sellerName;
    private String description;
    private String email;
    private String sellerNickName;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public OneSellerResponseDTO(Seller seller) {
        this.sellerName = seller.getSellerName();
        this.description = seller.getDescription();
        this.email = seller.getEmail();
        this.sellerNickName = seller.getNickname();
        this.createdAt = seller.getCreatedAt();
        this.modifiedAt = seller.getModifiedAt();
    }
}