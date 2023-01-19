package com.example.secondteamproject.repository;


import com.example.secondteamproject.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findAllByOrderByModifiedAtDesc();

    Seller findBySellerName(String name);
}
