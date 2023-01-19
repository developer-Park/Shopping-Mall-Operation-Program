package com.example.secondteamproject.userpackage.responseDTO;

import com.example.secondteamproject.seller.entity.Seller;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AllSellerListResponseDTO {
    private String sellerName;
    private String description;
    private String email;
    private String sellerNickName;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public AllSellerListResponseDTO(Seller seller) {
        this.sellerName = seller.getSellerName();
        this.description = seller.getDescription();
        this.email = seller.getEmail();
        this.sellerNickName = seller.getNickname();
        this.createdAt = seller.getCreatedAt();
        this.modifiedAt = seller.getModifiedAt();
    }
}
