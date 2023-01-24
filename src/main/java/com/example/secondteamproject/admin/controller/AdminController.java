package com.example.secondteamproject.admin.controller;


import com.example.secondteamproject.admin.dto.CustomerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestListResponseDto;
import com.example.secondteamproject.admin.dto.SellerRequestResponseDto;
import com.example.secondteamproject.admin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "3. admin")
public class AdminController {

    private final AdminService adminService;


    //고객리스트 조회, 10개씩 페이징
    @GetMapping("/customers")
    @ApiOperation(value = "고객 리스트 전체 조회", notes = "10개씩 페이징하여 고객 리스트 전체 조회")
    public List<CustomerListResponseDto> getAllCustomers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return adminService.getAllCustomer(pageable);
    }

    //판매자 조회, 10개씩 페이징
    @GetMapping("/sellers")
    @ApiOperation(value = "판매자 리스트 전체 조회", notes = "10개씩 페이징하여 판매자 리스트 전체 조회")
    public List<SellerListResponseDto> getAllSellers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return adminService.getAllSeller(pageable);
    }

    //판매자 조회, 10개씩 페이징
    //검색 옵션은 "이름", "별명", "이메일"
    @GetMapping("/sellers/search")
    @ApiOperation(value = "판매자 리스트 검색 조회", notes = "10개씩 페이징하여 판매자 리스트 검색 조회")
    public List<SellerListResponseDto> getAllSellersBySearching(@PageableDefault(size = 10, page = 0) Pageable pageable,
                                                                @RequestParam(value = "option") String option,
                                                                @RequestParam(value = "keyword") String keyword) {
        return adminService.getAllSellersBySearching(pageable, option, keyword);
    }

    //판매자 등록 요청폼 목록, 10개씩 페이징, 작성 오랜된 순으로 정렬
    @GetMapping("/seller-requests")
    @ApiOperation(value = "판매자 신청 리스트 조회", notes = "10개씩 페이징하여 판매자 신청 리스트 조회")
    public List<SellerRequestListResponseDto> getAllSellerRequests(@PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return adminService.getAllSellerRequest(pageable);
    }

    //판매자 등록요청폼 1개 보기.
    @GetMapping("/seller-requests/{id}")
    @ApiOperation(value = "판매자 신청 한 개 조회", notes = "하나의 판매자 신청 조회")
    public SellerRequestResponseDto getSellerRequest(@PathVariable Long id) {
        return adminService.getSellerRequest(id);
    }

    //판매자 등록요청폼 1개 승인. 유저DB에서 삭제 및 판매자DB에 저장
    @PostMapping("/seller-requests/{id}")
    @ApiOperation(value = "판매자 신청 승인", notes = "하나의 판매자 신청을 승인, 해당 사용자를 유저 DB 에서 삭제 후 판매자 DB 에 저장")
    public String approveSellerRequest(@PathVariable Long id) {
        adminService.approveSellerRequest(id);
        return "판매자 승인이 완료되었습니다.";
    }

    //판매자 등록요청폼 전체 승인. 유저DB에서 삭제 및 판매자DB에 저장
    @PostMapping("/seller-requests/")
    @ApiOperation(value = "판매자 신청 전체 승인", notes = "판매자 신청을 전체 승인, 사용자를 유저 DB 에서 삭제 후 판매자 DB 에 저장")
    public String approveAllSellerRequest() {
        adminService.approveAllSellerRequest();
        return "모든 판매자 승인이 완료되었습니다.";
    }

    //판매자 1개 권한 삭제. 판매자DB에서 삭제 및 유저DB에 저장
    @PostMapping("/sellers/{id}")
    @ApiOperation(value = "판매자 권한 삭제", notes = "특정 판매자의 권한을 삭제하여 일반 고객으로 전환")
    public String deleteSellerRole(@PathVariable Long id) {
        adminService.deleteSellerRole(id);
        return "success";
    }

}
