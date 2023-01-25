package com.example.secondteamproject.seller;


import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.userpackage.responseDTO.UserResponseFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;
    @PostMapping("/item")
    public ItemResponseDto createSellerItem(@RequestBody ItemRequestDto itemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return sellerService.createSellerItem(itemRequestDto,userDetails.getSeller());
    }
    @PutMapping("/item/{itemId}")
    public ItemResponseDto updateSellerItem(@PathVariable Long itemId,@RequestBody ItemRequestDto itemRequestDto) {
        return sellerService.updateSellerItem(itemRequestDto,itemId);
    }
    @DeleteMapping("/item/{itemId}")
    public String deleteSellerItem(@PathVariable Long itemId) {
        if (sellerService.deleteSellerItem(itemId)) {
            return "Success delete item";
        }
        else {
            return "Failed delete item";
        }
    }
    @PostMapping("/ongoing/{inquiryId}")
    public String ongoingProductInquiry(@PathVariable Long inquiryId) {
        sellerService.ongoingProductInquiry(inquiryId);
        return "confirm product inquiry";
    }

    @PostMapping("/answer/{inquiryId}")
    public ProductInquiryResponseDTO answerProductInquiry(@PathVariable Long inquiryId,@RequestBody ProductInquiryDTO productInquiryDTO) {
        return sellerService.answerProductInquiry(inquiryId,productInquiryDTO);
    }

    @PostMapping("/completed/{inquiryId}")
    public String completedProductInquiry(@PathVariable Long inquiryId) {
        sellerService.completedProductInquiry(inquiryId);
        return "Completed product inquiry";
    }

    //판매자 프로필 조회
    @GetMapping("/profile/{id}")
    public ResponseEntity<SellerResponseDto> findSeller(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.findSellerById(id));
    }

    //판매자 프로필 수정
    @PutMapping("/profile/{id}")
    public ResponseEntity<SellerResponseDto> updateSeller(@PathVariable Long id, @RequestBody SellerRequestDto sellerRequestDto){
        return  ResponseEntity.status(HttpStatus.OK).body(sellerService.update(id,sellerRequestDto));
    }
    //고객 요청 조회
    @GetMapping("/questions")
    public ResponseEntity<List<UserResponseFormDTO>> getQuestionsAll(){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getQuestionsAll());
    }

    @GetMapping("/items")
    public  ResponseEntity<List<ItemResponseDto>> getItemList(){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.getItemAll());
    }


}
