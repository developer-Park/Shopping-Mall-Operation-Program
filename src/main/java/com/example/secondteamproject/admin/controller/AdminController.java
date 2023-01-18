package com.example.secondteamproject.admin.controller;


import com.example.secondteamproject.admin.dto.CustomerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestResponseDto;
import com.example.secondteamproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//- 운영자
//    - 조회
//        - 고객 목록 : 고객들의 목록을 페이징하며 조회
//        - 판매자 목록 : 판매자들의 목록을 페이징하며 조회
//        - 판매자 등록 요청폼 목록 : 판매자 등록 요청목록을 조회
//    - 권한 등록
//        - 판매자 권한 승인 : 판매자 등록 요청을 승인
//    - 삭제
//        - 판매자 권한 : 유저의 판매자 권한을 삭제



//WebSecurityConfig에서 admin role만 접근하도록 바꿔주기
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    //고객리스트 조회, 10개씩 페이징
    @GetMapping("/customers")
    public List<CustomerListResponseDto> getAllCustomers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return adminService.getAllCustomer(pageable);
    }

    //판매자 조회, 10개씩 페이징
    @GetMapping("/sellers")
    public List<SellerListResponseDto> getAllSellers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return adminService.getAllSeller(pageable);
    }

    //판매자 등록 요청폼 목록, 10개씩 페이징, 작성 오랜된 순으로 정렬
    //승인 안된 것만 보이도록 따로 만들어야하나??
    @GetMapping("/seller-requests")
    public List<SellerRequestListResponseDto> getAllSellerRequests(@PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return adminService.getAllSellerRequest(pageable);
    }

    //판매자 등록요청폼 1개 보기.
    @GetMapping("/seller-requests/{id}")
    public SellerRequestResponseDto getSellerRequest(@PathVariable Long id) {
        return adminService.getSellerRequest(id);
    }

    //판매자 등록요청폼 1개 승인. 유저DB에서 삭제 및 판매자DB에 저장
    @PostMapping("/seller-requests/{id}")
    public String approveSellerRequest(@PathVariable Long id) {
        adminService.approveSellerRequest(id);
        return "판매자로 변경되어 요청 목록을 삭제합니다?";
    }

    //판매자 1개 권한 삭제. 판매자DB에서 삭제 및 유저DB에 저장
    @PostMapping("/sellers/{id}")
    public String deleteSellerRole(@PathVariable Long id) {
        adminService.deleteSellerRole(id);
        return "success";
    }

}
