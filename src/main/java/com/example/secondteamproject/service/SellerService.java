package com.example.secondteamproject.service;

import com.example.secondteamproject.dto.seller.SellerRequestDto;
import com.example.secondteamproject.dto.seller.SellerResponseDto;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.jwt.JwtUtil;
import com.example.secondteamproject.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
//
//    private final JwtUtil jwtUtil;

    //판매자 조회
    @Transactional
    public SellerRequestDto findSellerById(Long id ){
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        return new SellerRequestDto(seller);

    }
    //판매자 프로필 수정
//    public Long update(Long id,SellerRequestDto sellerRequestDto)




}
