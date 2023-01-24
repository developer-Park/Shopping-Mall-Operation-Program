package com.example.secondteamproject.seller;

import com.example.secondteamproject.category.Category;
import com.example.secondteamproject.category.CategoryRepository;
import com.example.secondteamproject.entity.*;
import com.example.secondteamproject.repository.*;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final ProductInquiryRepository productInquiryRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final AnswerProductInquiryRepository answerProductInquiryRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public ItemResponseDto createSellerItem(ItemRequestDto itemRequestDto, Seller seller) {
        Category category = categoryRepository.findByName(itemRequestDto.getCategory()).orElseThrow();
        Item checkItem = itemRepository.findByItemName(itemRequestDto.getItemName());
        if (checkItem != null) {
            throw new IllegalArgumentException("Exist item");
        }
        Item item = new Item(itemRequestDto, category, seller);
        itemRepository.save(item);
        return new ItemResponseDto(item);
    }

    @Transactional
    public ItemResponseDto updateSellerItem(ItemRequestDto itemRequestDto, Long itemId) {
        Item checkItem = itemRepository.findById(itemId).orElseThrow();
        Category category = categoryRepository.findByName(itemRequestDto.getCategory()).orElseThrow();
        checkItem.updateItem(itemRequestDto.getItemName(), category, itemRequestDto.getDescription(), itemRequestDto.getPrice());
        itemRepository.save(checkItem);
        return new ItemResponseDto(checkItem);
    }

    @Transactional
    public Boolean deleteSellerItem(Long itemId) {
        Item checkItem = itemRepository.findById(itemId).orElseThrow();
        itemRepository.delete(checkItem);
        return true;
    }

    @Transactional
    public void ongoingProductInquiry(Long inquiryId) {
        ProductInquiry productInquiry = productInquiryRepository.findById(inquiryId).orElseThrow();
        productInquiry.updateOnGoing();
        productInquiryRepository.save(productInquiry);
    }

    @Transactional
    public void completedProductInquiry(Long inquiryId) {
        ProductInquiry productInquiry = productInquiryRepository.findById(inquiryId).orElseThrow();
        productInquiry.updateOnGoing();
        productInquiryRepository.save(productInquiry);
    }

    @Transactional
    public ProductInquiryResponseDTO answerProductInquiry(Long inquiryId, ProductInquiryDTO productInquiryDTO) {
        ProductInquiry productInquiry = productInquiryRepository.findById(inquiryId).orElseThrow();
        AnswerProductInquiry answerProductInquiry = new AnswerProductInquiry(productInquiry.getSellerId().getSellerName(), productInquiryDTO.getAnswer(), productInquiry.getUserId().getUsername());
        answerProductInquiryRepository.save(answerProductInquiry);
        return new ProductInquiryResponseDTO(productInquiryDTO.getAnswer(), productInquiry.getSellerId().getSellerName(), productInquiry.getItemId().getItemName(), productInquiry.getUserId().getUsername());
    }


    //판매자 프로필 조회
    @Transactional
    public SellerResponseDto findSellerById(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        return new SellerResponseDto(seller);
    }

    //판매자 프로필 수정
    public SellerResponseDto update(Long id, SellerRequestDto sellerRequestDto) {
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정하고자 하는 프로필이 없습니다.")
        );
        seller.update(sellerRequestDto);
        return new SellerResponseDto(seller);
    }

    //고객 요청 목록 조회
    @Transactional
    public List<UserResponseFormDTO> getQuestionsAll() {
        List<ProductInquiry> questions = productInquiryRepository.findAllByOrderByModifiedAtDesc();
        List<UserResponseFormDTO> userResponseFormDTOList = new ArrayList<>();

        for (ProductInquiry productInquiry : questions) {
            userResponseFormDTOList.add(new UserResponseFormDTO(productInquiry));

        }
        return userResponseFormDTOList;

    }

}
