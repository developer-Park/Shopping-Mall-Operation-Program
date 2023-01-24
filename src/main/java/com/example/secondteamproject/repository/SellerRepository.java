package com.example.secondteamproject.repository;

import com.example.secondteamproject.entity.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findBySellerName(String name);
    List<Seller> findAllBySellerNameContainsIgnoreCase(String keyword);
    List<Seller> findAllByNicknameContainsIgnoreCase(String keyword);
    List<Seller> findAllByEmailContainsIgnoreCase(String keyword);
    List<Seller> findAllBySellerNameContainsIgnoreCaseOrNicknameContainsIgnoreCase(String keyword1, String keyword2);
}