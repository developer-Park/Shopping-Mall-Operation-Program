package com.example.secondteamproject.seller.service;

import com.example.secondteamproject.entity.ProductInquiry;
import com.example.secondteamproject.jwt.JwtUtil;
import com.example.secondteamproject.repository.ProductInquiryRepository;
import com.example.secondteamproject.seller.Dto.seller.SellerRequestDto;
import com.example.secondteamproject.seller.Dto.seller.SellerResponseDto;
import com.example.secondteamproject.repository.SellerRepository;
import com.example.secondteamproject.seller.entity.Seller;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseDTO;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    private final ProductInquiryRepository productInquiryRepository;




//
//    private final JwtUtil jwtUtil;

    //판매자 프로필 조회
    @Transactional
    public SellerResponseDto findSellerById(Long id){
        Seller seller = sellerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        return new SellerResponseDto(seller);
    }
    //판매자 프로필 수정
    @Transactional
    public SellerResponseDto update(Long id, SellerRequestDto sellerRequestDto){
        Seller seller =sellerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("수정하고자 하는 프로필이 없습니다.")
        );
        seller.update(sellerRequestDto);
        return new SellerResponseDto(seller);
    }
    //고객 요청 목록 조회
    @Transactional
    public List<UserResponseFormDTO> getQuestionsAll(){
//        List<ProductInquiry> questions = productInquiry.findAllByOrderByModifiedAtDesc();

        List<ProductInquiry>  questions  = productInquiryRepository.findAllByOrderByModifiedAtDesc();
        List<UserResponseFormDTO>  userResponseFormDTOList = new ArrayList<>();

        for (ProductInquiry productInquiry: questions) {
            userResponseFormDTOList.add(new UserResponseFormDTO(productInquiry));

        }
        return userResponseFormDTOList;

    }




}
