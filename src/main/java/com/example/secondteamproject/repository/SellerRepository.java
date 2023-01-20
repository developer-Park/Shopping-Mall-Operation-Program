package com.example.secondteamproject.repository;

import com.example.secondteamproject.entity.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findBySellerName(String name);
    Page<Seller> findAllBySellerNameContainsIgnoreCase(org.springframework.data.domain.Pageable pageable, String keyword);
    Page<Seller> findAllByNicknameContainsIgnoreCase(org.springframework.data.domain.Pageable pageable, String keyword);
    Page<Seller> findAllByEmailContainsIgnoreCase(Pageable pageable, String keyword);
}