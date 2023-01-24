package com.example.secondteamproject.userpackage;

import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.userpackage.requestDTO.SellerRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UpdateUserRequestDTO;
import com.example.secondteamproject.userpackage.requestDTO.UserRequestFormDTO;
import com.example.secondteamproject.userpackage.responseDTO.ItemResponseDTO;
import com.example.secondteamproject.userpackage.responseDTO.SellerResponseDTO;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseDTO;
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
public class UserController {

    private final UserService userService;





    @PostMapping("/{itemId}/forms")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProductInquiryToSeller(@PathVariable Long itemId, @RequestBody UserRequestFormDTO requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.createRequestFormToSeller(requestDto, userDetails.getUser().getId(), itemId);
        return new ResponseEntity<>("Success create product inquiry ", HttpStatus.CREATED);
    }

    @PostMapping("/seller")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createSellerRequestForm(@RequestBody SellerRequestDTO requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.createSellerRequestForm(requestDto, userDetails.getUser().getId());
        return new ResponseEntity<>("Success create request seller ", HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUserProfile(@PathVariable Long userId) {
        return userService.getOneUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserNickName(@PathVariable Long userId, @RequestBody UpdateUserRequestDTO requestDto) {
        userService.updateUserNickName(requestDto, userId);
        return new ResponseEntity<>("Success update user nickname ", HttpStatus.CREATED);
    }


    // 판매자 리스트 조회, 10개씩 페이징
    @GetMapping("/seller")
    public List<SellerResponseDTO> getSellerList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.getAllSellerList(userDetails, pageable);
    }

    // 상품 리스트 조회, 10개씩 페이징
    @GetMapping("/item")
    public List<ItemResponseDTO> getAllItemList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.getAllItemList(userDetails, pageable);
    }

    @GetMapping("/seller/{sellerId}")
    public SellerResponseDTO getOneSeller(@PathVariable Long sellerId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getOneSeller(sellerId);
    }

}


