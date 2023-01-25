package com.example.secondteamproject.repository;


import com.example.secondteamproject.entity.SellerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRequestRepository extends JpaRepository<SellerRequest, Long> {

    Optional<SellerRequest> findByUserId(Long userId);

}
