package com.example.secondteamproject.seller.controller;

import com.example.secondteamproject.seller.Dto.seller.SellerRequestDto;
import com.example.secondteamproject.seller.Dto.seller.SellerResponseDto;
import com.example.secondteamproject.seller.service.SellerService;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SellerController {

    @Autowired
    private SellerService sellerService;

    //판매자 프로필 조회

    @GetMapping("/seller/profile/{id}")
    public ResponseEntity<SellerResponseDto> findSeller(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.findSellerById(id));
    }

    //판매자 프로필 수정
    @PutMapping("/seller/profile/{id}")
    public ResponseEntity<SellerResponseDto> updateSeller(@PathVariable Long id, @RequestBody SellerRequestDto sellerRequestDto){
    return  ResponseEntity.status(HttpStatus.OK).body(sellerService.update(id,sellerRequestDto));
    }
    //고객 요청 조회
    @GetMapping("/seller/questions")
    public ResponseEntity<List<UserResponseFormDTO>> getQuestionsAll(){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getQuestionsAll());
    }








}
