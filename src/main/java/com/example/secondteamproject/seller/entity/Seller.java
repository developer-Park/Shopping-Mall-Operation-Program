package com.example.secondteamproject.seller.entity;

import com.example.secondteamproject.entity.Timestamped;
import com.example.secondteamproject.entity.UserRoleEnum;
import com.example.secondteamproject.seller.Dto.seller.SellerRequestDto;
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


    //파라미터에서 long id 삭제, description 추가
    public Seller(String sellerName, String password, String img, String nickname, String email, String description) {
        this.sellerName = sellerName;
        this.password = password;
        this.role = UserRoleEnum.SELLER;
        this.img = img;
        this.nickname = nickname;
        this.email = email;
        this.description = description;

    }

    public void update(SellerRequestDto sellerRequestDto){
        this.nickname = sellerRequestDto.getNickname();
        this.description =sellerRequestDto.getDescription();
        this.img = sellerRequestDto.getImg();
    }

    public boolean isValidSellerId(Long sellerId) {
        return this.id.equals(sellerId);
    }

    public boolean isAdmin(){
        return this.role.equals(UserRoleEnum.ADMIN);
    }
}



