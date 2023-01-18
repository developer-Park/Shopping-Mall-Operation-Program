package com.example.secondteamproject.entity;

import com.example.secondteamproject.dto.item.ItemRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "item")
public class Item extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;
    private String itemName;
    @ManyToOne
    @JoinColumn (name = "SELLER_ID")
    private Seller seller;
    private int price;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category itemCategory;

    public Item(ItemRequestDto itemRequestDto){
        this.itemName =itemRequestDto.getItemName();
        this.seller =itemRequestDto.getSeller();
        this.price =itemRequestDto.getPrice();
        this.description = itemRequestDto.getDescription();
    }

}
