package com.example.secondteamproject.seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductInquiryResponseDTO {

    String answer;
    String sellerName;
    String itemName;
    String userName;

    public ProductInquiryResponseDTO(String answer, String sellerName, String itemName, String userName) {
        this.answer = answer;
        this.sellerName = sellerName;
        this.itemName = itemName;
        this.userName = userName;
    }
}
