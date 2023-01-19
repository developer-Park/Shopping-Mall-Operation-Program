package com.example.secondteamproject.seller.Dto.item;

import com.example.secondteamproject.seller.entity.Item;
import com.example.secondteamproject.seller.entity.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemResponseDto {
    private String itemName;
    private Seller seller;
    private int price;
    private String description;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public ItemResponseDto(String itemName, Seller seller, int price, String description) {
        this.itemName = itemName;
        this.seller = seller;
        this.price = price;
        this.description = description;

    }

    public ItemResponseDto(Item item) {
        this.itemName = item.getItemName();
        this.seller = item.getSeller();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.modifiedAt =item.getModifiedAt();
        this.createdAt = item.getCreatedAt();
    }
}

