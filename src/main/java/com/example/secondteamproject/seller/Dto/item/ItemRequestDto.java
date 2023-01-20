package com.example.secondteamproject.seller.Dto.item;

//import com.example.secondteamproject.entity.Category;
import com.example.secondteamproject.category.Category;
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
    private Long sellerId;
    private String sellerName;
    private Long categoryId;


    public ItemRequestDto(String itemName, Seller seller, int price, String description,String sellerName) {
        this.itemName = itemName;
        this.seller = seller;
        this.price = price;
        this.description = description;
        this.sellerName =sellerName;
    }
}
