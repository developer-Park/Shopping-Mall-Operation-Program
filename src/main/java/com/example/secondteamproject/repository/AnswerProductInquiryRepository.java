package com.example.secondteamproject.repository;


import com.example.secondteamproject.entity.AnswerProductInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnswerProductInquiryRepository extends JpaRepository<AnswerProductInquiry, Long> {
}
