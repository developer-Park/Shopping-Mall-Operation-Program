package com.example.secondteamproject.seller;

import com.example.secondteamproject.entity.Seller;

public interface SellerService {
    ItemResponseDto createSellerItem(ItemRequestDto itemRequestDto, Seller seller);

}
