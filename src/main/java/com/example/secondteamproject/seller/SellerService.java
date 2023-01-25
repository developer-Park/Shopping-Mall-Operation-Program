package com.example.secondteamproject.seller;

import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseFormDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SellerService {
    ItemResponseDto createSellerItem(ItemRequestDto itemRequestDto, Seller seller);

    ItemResponseDto updateSellerItem(ItemRequestDto itemRequestDto, Long itemId);

    Boolean deleteSellerItem(Long itemId);


    void ongoingProductInquiry(Long inquiryId);

    void completedProductInquiry(Long inquiryId);

    ProductInquiryResponseDTO answerProductInquiry(Long inquiryId, ProductInquiryDTO productInquiryDTO);

    SellerResponseDto findSellerById(Long id);

    SellerResponseDto update(Long id, SellerRequestDto sellerRequestDto);

    List<UserResponseFormDTO> getQuestionsAll();

    List<ItemResponseDto> getItemAll();
}
