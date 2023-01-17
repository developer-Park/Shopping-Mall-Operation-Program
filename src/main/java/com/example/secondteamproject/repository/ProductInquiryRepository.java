package com.example.secondteamproject.repository;


import com.example.secondteamproject.entity.ProductInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductInquiryRepository extends JpaRepository<ProductInquiry, Long> {
}
