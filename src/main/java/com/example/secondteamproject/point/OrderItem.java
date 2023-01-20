package com.example.secondteamproject.point;

import com.example.secondteamproject.entity.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "PointReception")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINTRECEPTION_ID")
    private Long id;
    private String itemName;
    private Integer point;
    private Integer price;
    @Enumerated(value = EnumType.STRING)
    private StatusEnum statusEnum;

    private String userName;

    private String sellerName;



    public OrderItem(String itemName, Integer price, Integer point, String username, String sellername){
        this.itemName =itemName;
        this.price =price;
        this.point =point;
        this.statusEnum = StatusEnum.WAITING;
        this.userName = username;
        this.sellerName=sellername;
    }

    public void updateStatus(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;

    }
}
