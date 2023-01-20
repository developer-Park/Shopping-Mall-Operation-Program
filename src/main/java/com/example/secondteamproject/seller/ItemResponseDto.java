package com.example.secondteamproject.seller;


import com.example.secondteamproject.category.Category;
import com.example.secondteamproject.entity.Item;
import com.example.secondteamproject.entity.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponseDto {
    private String itemName;
    private String sellerName;
    private int price;
    private String description;

    private String category;

    public ItemResponseDto(Item item) {
        this.itemName = item.getItemName();
        this.sellerName = item.getSeller().getSellerName();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.category = item.getItemCategory().getName();
    }

}