package com.example.secondteamproject.entity;

import com.example.secondteamproject.userpackage.requestDTO.UserRequestFormDTO;
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

    private String title;
    private String content;

    private String email;

    @Enumerated(value = EnumType.STRING)
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

    public ProductInquiry(UserRequestFormDTO userRequestFormDTO,User userId, Item itemId) {
        this.title = userRequestFormDTO.getTitle();
        this.content = userRequestFormDTO.getContent();
        this.statusEnum = StatusEnum.WAITING;
        this.userId =userId;
        this.email = userId.getEmail();
        this.itemId = itemId;
        this.sellerId = itemId.getSeller();
    }

    public void updateOnGoing() {
        this.statusEnum = StatusEnum.ONGOING;
    }
}
