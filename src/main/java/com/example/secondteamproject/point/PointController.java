package com.example.secondteamproject.point;

import com.example.secondteamproject.security.UserDetailsImpl;
import com.example.secondteamproject.userpackage.responseDTO.AllItemListResponseDTO;
import com.example.secondteamproject.userpackage.responseDTO.OneSellerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//Point Park
//Writer : Park
@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;

    @GetMapping("/users/{userId}")
    public PointResponseDTO checkUserPoint(@PathVariable Long userId) {
        return pointService.getUserPoint(userId);
    }

    @GetMapping("/sellers/{sellerId}")
    public PointResponseDTO checkSellerPoint(@PathVariable Long sellerId) {
        return pointService.getSellerPoint(sellerId);
    }

    @PostMapping("/pay-point/{itemId}")
    public PointResponseDTO orderItemPayByPoint(@PathVariable Long itemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return pointService.payPointForUser(itemId, userDetails.getUser());
    }

    @PutMapping("/complete/{orderItemId}")
    public ResponseEntity<String> productSaleCompleted(@PathVariable Long orderItemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        pointService.orderItemCompleted(orderItemId, userDetails.getUser());
        return new ResponseEntity<>("Completed order ", HttpStatus.CREATED);
    }

    @PutMapping("/receivepoint/{orderItemId}")
    public PointResponseDTO receivePointSeller(@PathVariable Long orderItemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return pointService.receivePoint(orderItemId, userDetails.getSeller());
    }
    @PutMapping("/addpoint/users")
    public String addPointForUser(@RequestBody PointAddRequestDTO pointAddRequestDTO)  {
        pointService.addPointUser(pointAddRequestDTO);
        return "Success add user points";
    }
    @PutMapping("/addpoint/sellers")
    public String addPointForSeller(@RequestBody PointAddRequestDTO pointAddRequestDTO)  {
        pointService.addPointSeller(pointAddRequestDTO);
        return "Success add seller points";
    }
}


