package com.example.secondteamproject.dto.item;

import com.example.secondteamproject.entity.Item;
import com.example.secondteamproject.entity.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponseDto {
    private String itemName;
    private Seller seller;
    private int price;
    private String description;


    public ItemResponseDto(String itemName, Seller seller, int price, String description) {
        this.itemName = itemName;
        this.seller = seller;
        this.price = price;
        this.description = description;

    }

    public ItemResponseDto(Item item) {
    }
}

