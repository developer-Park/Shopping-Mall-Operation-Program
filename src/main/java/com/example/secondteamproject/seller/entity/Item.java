package com.example.secondteamproject.seller.entity;

import com.example.secondteamproject.category.Category;
import com.example.secondteamproject.entity.Timestamped;
import com.example.secondteamproject.seller.Dto.item.ItemRequestDto;
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
    public  void updateItem(ItemRequestDto itemRequestDto){
        this.itemName =itemRequestDto.getItemName();
        this.seller =itemRequestDto.getSeller();
        this.price =itemRequestDto.getPrice();
        this.description = itemRequestDto.getDescription();
    }

//    public boolean isWriter(Long itemid){
//        return this.id.equals(itemid);
//    }

}
