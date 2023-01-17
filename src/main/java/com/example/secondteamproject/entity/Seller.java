package com.example.secondteamproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "sellers")
public class Seller extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SELLER_ID")
    private Long id;
    @Column(nullable = false, unique = true)
    private String sellerName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private String description;

    private String img;
    private String nickname;

    private String email;

    public Seller(Long id, String sellerName, String password, UserRoleEnum role, String img,String nickname) {
        this.id = id;
        this.sellerName = sellerName;
        this.password = password;
        this.role = role;
        this.img = img;
        this.nickname=nickname;
    }

}
