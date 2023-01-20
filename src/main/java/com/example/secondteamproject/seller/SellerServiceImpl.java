package com.example.secondteamproject.seller;

import com.example.secondteamproject.category.Category;
import com.example.secondteamproject.category.CategoryRepository;
import com.example.secondteamproject.entity.Item;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.repository.ItemRepository;
import com.example.secondteamproject.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

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
}