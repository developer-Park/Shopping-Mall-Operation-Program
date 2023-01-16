package com.example.secondteamproject.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;
    private String categoryName;
    private String parent;
    private int layer = 0;

    public Category(String name, String parent, int layer) {
        this.categoryName = name;
        this.parent = parent;
        this.layer = layer;
    }
}
