package com.example.secondteamproject.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderItemRepositroy extends JpaRepository<OrderItem, Long>{
}
