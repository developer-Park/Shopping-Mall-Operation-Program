package com.example.secondteamproject.userpackage;

import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.userpackage.requestDTO.SellerRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UpdateUserRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UserRequestFormDTO;
import com.example.secondteamproject.userpackage.responseDTO.ItemResponseDTO;
import com.example.secondteamproject.userpackage.responseDTO.SellerResponseDTO;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Api(tags = "1. user")
public class UserController {

    private final UserService userService;

    @PostMapping("/{itemId}/forms")
    @ApiOperation(value = "제품문의", notes = "판매자에게 특정 상품 관련 제품 문의")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProductInquiryToSeller(@PathVariable Long itemId, @RequestBody UserRequestFormDTO requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.createRequestFormToSeller(requestDto, userDetails.getUser().getId(), itemId);
        return new ResponseEntity<>("Success create product inquiry ", HttpStatus.CREATED);
    }

    @PostMapping("/seller")
    @ApiOperation(value = "판매자 신청", notes = "관리자에게 판매자로 승격 신청")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createSellerRequestForm(@RequestBody SellerRequestDTO requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.createSellerRequestForm(requestDto, userDetails.getUser().getId());
        return new ResponseEntity<>("Success create request seller ", HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "고객 프로필 조회", notes = "고객 프로필을 조회")
    public UserResponseDTO getUserProfile(@PathVariable Long userId) {
        return userService.getOneUser(userId);
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "닉네임 설정", notes = "고객 닉네임을 설정")
    public ResponseEntity<String> updateUserNickName(@PathVariable Long userId, @RequestBody UpdateUserRequestDTO requestDto) {
        userService.updateUserNickName(requestDto, userId);
        return new ResponseEntity<>("Success update user nickname ", HttpStatus.CREATED);
    }


    // 판매자 리스트 조회, 10개씩 페이징
    @GetMapping("/seller")
    @ApiOperation(value = "판매자 리스트 조회", notes = "10개씩 페이징하여 판매자 정보 리스트 조회")
    public List<SellerResponseDTO> getSellerList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.getAllSellerList(userDetails, pageable);
    }

    // 상품 리스트 조회, 10개씩 페이징
    @GetMapping("/item")
    @ApiOperation(value = "상품 리스트 조회", notes = "10개씩 페이징하여 상품 정보 리스트 조회")
    public List<ItemResponseDTO> getAllItemList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.getAllItemList(userDetails, pageable);
    }

    @GetMapping("/seller/{sellerId}")
    @ApiOperation(value = "판매자 조회", notes = "판매자 한 명의 정보를 조회")
    public SellerResponseDTO getOneSeller(@PathVariable Long sellerId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getOneSeller(sellerId);
    }

}


