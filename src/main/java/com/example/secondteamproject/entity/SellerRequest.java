package com.example.secondteamproject.entity;

import com.example.secondteamproject.userpackage.requestDTO.SellerRequestDTO;
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
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String title;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum statusEnum;

    public SellerRequest(SellerRequestDTO sellerRequestDTO, User user) {
        this.user = user;
        this.statusEnum = StatusEnum.WAITING;
        this.content = sellerRequestDTO.getContent();
        this.title = sellerRequestDTO.getTitle();
    }
}
