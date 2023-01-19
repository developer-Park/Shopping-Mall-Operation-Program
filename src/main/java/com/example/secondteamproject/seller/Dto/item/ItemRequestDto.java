package com.example.secondteamproject.seller.Dto.item;

//import com.example.secondteamproject.entity.Category;
import com.example.secondteamproject.seller.entity.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRequestDto {

    private String itemName;
    private Seller seller;
    private int price;
    private String description;


    public ItemRequestDto(String itemName, Seller seller, int price, String description) {
        this.itemName = itemName;
        this.seller = seller;
        this.price = price;
        this.description = description;
    }
}
