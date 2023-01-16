package com.example.secondteamproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "productinquiry")
public class ProductInquiry extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCTINQUIRY_ID")
    private Long id;

    private String content;
    private StatusEnum statusEnum;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    private Seller sellerId;
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item itemId;
}
