package com.example.secondteamproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "answerproductinquiry")
public class AnswerProductInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWERPRODUCTINQUIRY_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    private Seller sellername;

    private String contentAnswer;
    @ManyToOne
    @JoinColumn(name="PRODUCTINQUIRY_ID")
    private ProductInquiry inquiryId;
}
