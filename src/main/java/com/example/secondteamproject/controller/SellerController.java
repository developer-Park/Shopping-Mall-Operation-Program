package com.example.secondteamproject.controller;

import com.example.secondteamproject.dto.seller.SellerRequestDto;
import com.example.secondteamproject.dto.seller.SellerResponseDto;
import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SellerController {

    private SellerService sellerService;

    //판매자 프로필 조회
    @GetMapping("/seller/profile/{id}")
    public ResponseEntity<SellerRequestDto> findSeller(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.findSellerById(id));
    }
    //판매자 프로필 생성
//    @PostMapping("seller/profile")
//    public ResponseEntity<SellerRequestDto> createSeller(@RequestBody SellerRequestDto sellerRequestDto){
//        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.createSeller(sellerRequestDto));
//    }
    //판매상품 등록
    //판매자 프로필 수정
//    @PutMapping("seller/pofile/{id}")
//    public  ResponseEntity<SellerRequestDto> updateSeller(@PathVariable Long id)







}
