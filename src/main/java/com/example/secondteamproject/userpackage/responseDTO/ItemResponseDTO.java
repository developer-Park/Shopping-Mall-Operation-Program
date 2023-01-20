package com.example.secondteamproject.userpackage.responseDTO;

import com.example.secondteamproject.entity.Item;
import lombok.Getter;

@Getter
public class ItemResponseDTO {
    private String itemName;
    private String description;
    private String sellerName;
    private int price;
    private String category;

    private Integer point;


    public ItemResponseDTO(Item item) {
        this.itemName = item.getItemName();
        this.description = item.getDescription();
        this.sellerName = item.getSeller().getSellerName();
        this.price = item.getPrice();
        this.category = item.getItemCategory().getName();
        this.point= item.getPoint();
    }
}
