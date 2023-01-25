package com.example.secondteamproject.seller.controller;

import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.seller.Dto.item.ItemRequestDto;
import com.example.secondteamproject.seller.Dto.item.ItemResponseDto;
import com.example.secondteamproject.seller.entity.Item;
import com.example.secondteamproject.seller.entity.Seller;
import com.example.secondteamproject.seller.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemController {

    @Autowired
    private final ItemService itemService;

    // 판매상품 등록
//    @Secured("Seller")
    @PostMapping("/seller/items")
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemRequestDto itemRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(itemRequestDto));
    }
    //판매상품 수정

    @PutMapping("/seller/items/{id}")
    public ResponseEntity<ItemResponseDto>  updateItem(@PathVariable Long id, @RequestBody ItemRequestDto itemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.updateItem(id,itemRequestDto,userDetails.getSeller()));
    }
    //판매상품 조회
//    @Secured("Seller")
    @GetMapping("/seller/items")
    public  ResponseEntity<List<ItemResponseDto>> getItemList(){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemAll());
    }
    //판매상품 삭제
//    @Secured("Seller")
    @DeleteMapping("/seller/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return itemService.delete(id,userDetails.getSeller());
    }

//    // 관심 상품 조회하기
//    @GetMapping("/products")
//    public Page<Item> getProducts(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam("sortBy") String sortBy,
//            @RequestParam("isAsc") boolean isAsc,
//            HttpServletRequest request
//    ) {
//        // 응답 보내기
//        return itemService.getProducts(request, page-1, size, sortBy, isAsc);
//    }



}
