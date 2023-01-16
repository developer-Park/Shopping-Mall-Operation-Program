package com.example.secondteamproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "sellerrequest")
public class SellerRequest  extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SELLERREQUEST_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;

    private StatusEnum statusEnum;

    private Long adminId;


}
