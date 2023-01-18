package com.example.secondteamproject.service;

import com.example.secondteamproject.dto.item.ItemRequestDto;
import com.example.secondteamproject.dto.item.ItemResponseDto;
import com.example.secondteamproject.entity.Item;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;


    //판매상품 등록
    @Transactional
    public ItemResponseDto createItem(ItemRequestDto itemRequestDto  ){
        Item item = new Item(itemRequestDto);
        itemRepository.saveAndFlush(item);
        return new ItemResponseDto(item);
    }

}
