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

    //판매자 조회, 10개씩 페이징
    //검색 옵션은 "이름", "별명", "이메일"
    @GetMapping("/sellers/search")
    public List<SellerListResponseDto> getAllSellersBySearching(@PageableDefault(size = 10, page = 0) Pageable pageable,
                                                                @RequestParam(value = "option") String option,
                                                                @RequestParam(value = "keyword") String keyword) {
        return adminService.getAllSellersBySearching(pageable, option, keyword);
    }

    //판매자 등록 요청폼 목록, 10개씩 페이징, 작성 오랜된 순으로 정렬
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
        return "판매자 승인이 완료되었습니다.";
    }

    //판매자 등록요청폼 전체 승인. 유저DB에서 삭제 및 판매자DB에 저장
    @PostMapping("/seller-requests/")
    public String approveAllSellerRequest() {
        adminService.approveAllSellerRequest();
        return "모든 판매자 승인이 완료되었습니다.";
    }

    //판매자 1개 권한 삭제. 판매자DB에서 삭제 및 유저DB에 저장
    @PostMapping("/sellers/{id}")
    public String deleteSellerRole(@PathVariable Long id) {
        adminService.deleteSellerRole(id);
        return "success";
    }

    //판매자 등록요청폼 전체 승인. 유저DB에서 삭제 및 판매자DB에 저장
    @PostMapping("/seller-requests/")
    public String approveAllSellerRequest() {
        adminService.approveAllSellerRequest();
        return "모든 판매자 승인이 완료되었습니다.";
    }

}
