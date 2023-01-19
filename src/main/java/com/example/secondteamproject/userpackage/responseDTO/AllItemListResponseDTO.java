package com.example.secondteamproject.userpackage.responseDTO;

import com.example.secondteamproject.seller.entity.Item;
import lombok.Getter;

@Getter
public class AllItemListResponseDTO {
    private String itemName;
    private String description;
    private String sellerName;
    private int price;
    private String category;


    public AllItemListResponseDTO(Item item) {
        this.itemName = item.getItemName();
        this.description = item.getDescription();
        this.sellerName = item.getSeller().getSellerName();
        this.price = item.getPrice();
        this.category = item.getItemCategory().getName();
    }
}
