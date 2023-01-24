package com.example.secondteamproject.point;

import com.example.secondteamproject.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "point")
public class PointController {

    private final PointService pointService;

    @GetMapping("/users/{userId}")
    @ApiOperation(value = "고객 포인트 조회", notes = "고객의 잔여 포인트를 조회")
    public PointResponseDTO checkUserPoint(@PathVariable Long userId) {
        return pointService.getUserPoint(userId);
    }

    @GetMapping("/sellers/{sellerId}")
    @ApiOperation(value = "판매자 포인트 조회", notes = "판매자의 잔여 포인트를 조회")
    public PointResponseDTO checkSellerPoint(@PathVariable Long sellerId) {
        return pointService.getSellerPoint(sellerId);
    }

    @PostMapping("/pay-point/{itemId}")
    @ApiOperation(value = "고객 포인트 사용", notes = "가격 확인 후 고객의 잔여 포인트 사용")
    public PointResponseDTO orderItemPayByPoint(@PathVariable Long itemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return pointService.payPointForUser(itemId, userDetails.getUser());
    }

    @PutMapping("/complete/{orderItemId}")
    @ApiOperation(value = "상품 주문", notes = "주문 리스트에 해당 상품을 추가")
    public ResponseEntity<String> productSaleCompleted(@PathVariable Long orderItemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        pointService.orderItemCompleted(orderItemId, userDetails.getUser());
        return new ResponseEntity<>("Completed order ", HttpStatus.CREATED);
    }

    @PutMapping("/receivepoint/{orderItemId}")
    @ApiOperation(value = "판매자 포인트 적립", notes = "판매된 상품의 가격만큼 판매자에게 포인트 적립")
    public PointResponseDTO receivePointSeller(@PathVariable Long orderItemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return pointService.receivePoint(orderItemId, userDetails.getSeller());
    }
    @PutMapping("/addpoint/users")
    @ApiOperation(value = "고객 포인트 부여", notes = "고객에게 포인트 부여")
    public String addPointForUser(@RequestBody PointAddRequestDTO pointAddRequestDTO)  {
        pointService.addPointUser(pointAddRequestDTO);
        return "Success add user points";
    }
    @PutMapping("/addpoint/sellers")
    @ApiOperation(value = "판매자 포인트 부여", notes = "판매자에게 포인트 부여")
    public String addPointForSeller(@RequestBody PointAddRequestDTO pointAddRequestDTO)  {
        pointService.addPointSeller(pointAddRequestDTO);
        return "Success add seller points";
    }
}


