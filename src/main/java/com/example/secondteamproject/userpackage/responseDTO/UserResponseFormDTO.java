package com.example.secondteamproject.userpackage.responseDTO;

import com.example.secondteamproject.category.Category;
import com.example.secondteamproject.entity.ProductInquiry;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class UserResponseFormDTO {
    private Long id;
    private String title;
    private String username;
    private String email;
    private String itemName;
    private String contents;
    private String categoryName;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;


    public UserResponseFormDTO(ProductInquiry productInquiry) {
        this.id = productInquiry.getId();
        this.title = productInquiry.getTitle();
        this.username = productInquiry.getUserId().getUsername();
        this.email = productInquiry.getUserId().getEmail();
        this.itemName = productInquiry.getItemId().getItemName();
        this.contents = productInquiry.getContent();
        this.categoryName = productInquiry.getItemId().getItemCategory().getName();
        this.modifiedAt = productInquiry.getModifiedAt();
        this.createdAt = productInquiry.getCreatedAt();
    }

//    public UserResponseFormDTO(ProductInquiry questions) {
//        this.id =questions.getId();
//        this.title =questions.getTitle();
//        this.username=questions.getUserId().getUsername();
//        this.email=questions.getUserId().getEmail();
//        this.itemName = questions.getItemId().getItemName();
//        this.contents = questions.getContent();
//        this.categoryName = questions.getItemId().getItemCategory().getName();
//        this.modifiedAt = questions.getModifiedAt();
//        this.createdAt = questions.getCreatedAt();
//
//
//    }
}
