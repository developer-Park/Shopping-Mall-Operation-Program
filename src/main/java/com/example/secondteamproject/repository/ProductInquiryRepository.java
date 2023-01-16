package com.example.secondteamproject.repository;


import com.example.secondteamproject.entity.ProductInquiry;
import com.example.secondteamproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductInquiryRepository extends JpaRepository<ProductInquiry, Long> {
}
