package com.example.secondteamproject.controller;

import com.example.secondteamproject.dto.item.ItemRequestDto;
import com.example.secondteamproject.dto.item.ItemResponseDto;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;

    @Secured("seller")
    @PostMapping("seller/items")
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemRequestDto itemRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(itemRequestDto));
    }


}
