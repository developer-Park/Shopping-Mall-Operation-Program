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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
        Seller seller = sellerRepository.findById(itemRequestDto.getSellerId()).orElseThrow(
                ()-> new IllegalArgumentException("해당 판매자가 없습니다.")
        );
        Item item = new Item(itemRequestDto,category,seller);
        itemRepository.saveAndFlush(item);
        return new ItemResponseDto(item);
    }
    //판매상품 수정
    @Transactional
    public ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto,Seller seller) {

        Item item = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정하고자하는 상품이 없습니다.")
        );

        if (item.isWriter(seller.getId())) {
            item.updateItem(itemRequestDto);
            return new ItemResponseDto(item);
        } else {
                throw new IllegalArgumentException("해당 판매자 또는 관리자가 아니면 해당 상품을 수정할 수 없습니다!");
        }
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



//    판매상품 삭제
    @Transactional
    public ResponseEntity<String> delete(Long id,Seller seller) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제하고자 하는 상품이 없습니다.")
        );
        if (item.isWriter(seller.getId())) {
            itemRepository.delete(item);
            return new ResponseEntity<>("아이템 삭제 성공", HttpStatus.OK);
        } else {
            throw new IllegalArgumentException("해당 판매자 혹은 관리자가 아니면 해당 상품을 삭제할 수 없습니다.");
        }
    }



//    @Transactional(readOnly = true)
//    public Page<Item> getProducts(HttpServletRequest request,
//                                  int page, int size, String sortBy, boolean isAsc) {
//        // 페이징 처리
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        items = itemRepository.findAll(pageable);
//        return items;
//    }


}