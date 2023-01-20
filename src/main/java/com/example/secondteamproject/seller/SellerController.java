package com.example.secondteamproject.seller;


import com.example.secondteamproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;
    @PostMapping("/item")
    public ItemResponseDto createSellerItem(@RequestBody ItemRequestDto itemRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return sellerService.createSellerItem(itemRequestDto,userDetails.getSeller());
    }
}
