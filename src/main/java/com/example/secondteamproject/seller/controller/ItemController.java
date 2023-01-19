package com.example.secondteamproject.seller.controller;

import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.seller.Dto.item.ItemRequestDto;
import com.example.secondteamproject.seller.Dto.item.ItemResponseDto;
import com.example.secondteamproject.seller.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;

    // 판매상품 등록
    @Secured("seller")
    @PostMapping("/seller/items")
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemRequestDto itemRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(itemRequestDto));
    }
    //판매상품 수정
    @Secured("Seller")
    @PutMapping("/seller/items/{id}")
    public ResponseEntity<ItemResponseDto>  updateItem(@PathVariable Long id,@RequestBody ItemRequestDto itemRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(id,itemRequestDto));
    }
    //판매상품 조회
    @Secured("Seller")
    @GetMapping("/seller/items")
    public  ResponseEntity<List<ItemResponseDto>> getItemList(){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemAll());
    }
    //판매상품 삭제
    @Secured("Seller")
    @DeleteMapping("/seller/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id){
        return itemService.delete(id);
    }


}
