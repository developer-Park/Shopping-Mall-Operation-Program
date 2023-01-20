package com.example.secondteamproject.entity;

import com.example.secondteamproject.category.Category;
import com.example.secondteamproject.category.CategoryRepository;
import com.example.secondteamproject.seller.ItemRequestDto;
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
    private Integer price;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category itemCategory;

    private Integer point;

    public Item(ItemRequestDto itemRequestDto, Category category,Seller seller){
        this.itemName =itemRequestDto.getItemName();
        this.seller = seller;
        this.price =itemRequestDto.getPrice();
        this.description = itemRequestDto.getDescription();
        this.point = price / 10 ;
        this.itemCategory = category;
    }


}
