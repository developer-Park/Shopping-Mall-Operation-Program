package com.example.secondteamproject.admin.service;

import com.example.secondteamproject.admin.dto.CustomerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    List<CustomerListResponseDto> getAllCustomer(Pageable pageable);
    List<SellerListResponseDto> getAllSeller(Pageable pageable);
    List<SellerListResponseDto> getAllSellersBySearching(Pageable pageable, String option, String keyword);
    List<SellerRequestListResponseDto> getAllSellerRequest(Pageable pageable);
    SellerRequestResponseDto getSellerRequest(Long id);
    void approveSellerRequest(Long id);
    void deleteSellerRole(Long id);


    void approveAllSellerRequest();

}