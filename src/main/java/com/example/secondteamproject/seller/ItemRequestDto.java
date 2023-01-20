package com.example.secondteamproject.seller;


import com.example.secondteamproject.entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRequestDto {

    private String itemName;
    private int price;
    private String description;
    private String category;


}