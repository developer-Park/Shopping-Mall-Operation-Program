package com.example.secondteamproject.seller.service;

import com.example.secondteamproject.category.Category;
import com.example.secondteamproject.category.CategoryRepository;
import com.example.secondteamproject.repository.SellerRepository;
import com.example.secondteamproject.seller.Dto.item.ItemRequestDto;
import com.example.secondteamproject.seller.Dto.item.ItemResponseDto;
import com.example.secondteamproject.repository.ItemRepository;
import com.example.secondteamproject.seller.entity.Item;
import com.example.secondteamproject.seller.entity.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    private final SellerRepository sellerRepository;


    //판매상품 등록
    @Transactional
    public ItemResponseDto createItem(ItemRequestDto itemRequestDto){
        Category category = categoryRepository.findById(itemRequestDto.getCategoryId()).orElseThrow(
                ()-> new IllegalArgumentException("생성하고자 하는 아이템의 카테고리가 없습니다.")
        );
//        Seller seller = sellerRepository.findById(itemRequestDto.getSellerId()).orElseThrow(
//                ()-> new IllegalArgumentException("해당 판매자가 없습니다.")
//        );
        Item item = new Item(itemRequestDto,category);
        itemRepository.saveAndFlush(item);
        return new ItemResponseDto(item);
    }
    //판매상품 수정
    @Transactional
    public ItemResponseDto updateItem(Long id,ItemRequestDto itemRequestDto){

        Item item = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정하고자하는 상품이 없습니다.")
        );
        item.updateItem(itemRequestDto);
        return new ItemResponseDto(item);
    }
    //판매상품 조회
    @Transactional
    public List<ItemResponseDto> getItemAll(){

        List<Item> items = itemRepository.findAllByOrderByModifiedAtDesc();
        List<ItemResponseDto> ItemResponseDtoList = new ArrayList<>();

        for (Item item : items) {
            ItemResponseDtoList.add(new ItemResponseDto(item));
        }
        return ItemResponseDtoList;
    }

    //판매상품 삭제
    @Transactional
    public ResponseEntity<String> delete(Long id){
        Item item =itemRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("삭제하고자 하는 상품이 없습니다.")
        ) ;
//        if (item.isW)
        itemRepository.delete(item);
        return  new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
    }


}