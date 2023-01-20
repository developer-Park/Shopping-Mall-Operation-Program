package com.example.secondteamproject.point;

import com.example.secondteamproject.entity.Admin;
import com.example.secondteamproject.entity.Seller;
import com.example.secondteamproject.entity.User;

public interface PointService {
    PointResponseDTO getUserPoint(Long userId);

    PointResponseDTO getSellerPoint(Long sellerId);

    PointResponseDTO payPointForUser(Long itemId, User user);

    void orderItemCompleted(Long orderItemId, User user);

    PointResponseDTO receivePoint(Long orderItemId, Seller seller);

    void addPointUser(PointAddRequestDTO pointAddRequestDTO);
    void addPointSeller(PointAddRequestDTO pointAddRequestDTO);

    }
