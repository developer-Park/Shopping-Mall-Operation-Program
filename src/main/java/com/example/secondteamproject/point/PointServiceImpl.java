package com.example.secondteamproject.point;


import com.example.secondteamproject.entity.*;
import com.example.secondteamproject.repository.ItemRepository;
import com.example.secondteamproject.repository.SellerRepository;
import com.example.secondteamproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final OrderItemRepositroy orderItemRepositroy;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ItemRepository itemRepository;

    public PointResponseDTO getUserPoint(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return new PointResponseDTO(user.getPoint(), user.getUsername());
    }

    public PointResponseDTO getSellerPoint(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow();
        return new PointResponseDTO(seller.getPoint(), seller.getSellerName());
    }

    public PointResponseDTO payPointForUser(Long itemId, User user) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        if (item.getPrice() <= user.getPoint()) {
            Integer minusPoint=user.getPoint() - item.getPrice();
            user.updatePoint(minusPoint);
            userRepository.save(user);
            OrderItem orderItem = new OrderItem(item.getItemName(), item.getPrice(), item.getPoint(), user.getUsername(), item.getSeller().getSellerName());
            orderItemRepositroy.save(orderItem);
            return new PointResponseDTO(user.getPoint(), user.getUsername());
        } else {
            throw new IllegalArgumentException("less points");
        }
    }

    public void orderItemCompleted(Long orderItemId, User user) {
        OrderItem orderItem = orderItemRepositroy.findById(orderItemId).orElseThrow();
        if (Objects.equals(user.getUsername(), orderItem.getUserName())) {
            orderItem.updateStatus(StatusEnum.COMPLETED);
            orderItemRepositroy.save(orderItem);
        }else {
            throw new IllegalArgumentException("Failed complete order");
        }
    }

    public PointResponseDTO receivePoint(Long orderItemId, Seller seller) {
        OrderItem checkOrderItem = orderItemRepositroy.findById(orderItemId).orElseThrow();
        if(checkOrderItem.getStatusEnum()==StatusEnum.COMPLETED) {
            Integer plusPoint= seller.getPoint()+checkOrderItem.getPrice();
            seller.updatePointBySeller(plusPoint);
            sellerRepository.save(seller);
            return new PointResponseDTO(seller.getPoint(), seller.getSellerName());
        }
        throw new IllegalStateException("Failed receive points , Can not find item in repository");
    }

    public void addPointUser(PointAddRequestDTO pointAddRequestDTO) {
        User user = userRepository.findByUsername(pointAddRequestDTO.getName());
        if(user == null) {
            throw new IllegalArgumentException("Not exist user");
        }
        Integer plusPoint= user.getPoint()+ pointAddRequestDTO.getPoint();
        user.updatePoint(plusPoint);
        userRepository.save(user);
    }

    public void addPointSeller(PointAddRequestDTO pointAddRequestDTO) {
        Seller seller = sellerRepository.findBySellerName(pointAddRequestDTO.getName());
        if(seller == null) {
            throw new IllegalArgumentException("Not exist user");
        }
        Integer plusPoint= seller.getPoint()+ pointAddRequestDTO.getPoint();
        seller.updatePointBySeller(plusPoint);
        sellerRepository.save(seller);
    }
}

