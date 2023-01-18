package com.example.secondteamproject.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Category Package Writer By Park
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;
    private String parent;
    private int layer = 0;

    public Category(String name, String parent, int layer) {
        this.name = name;
        this.parent = parent;
        this.layer = layer;
    }

    public Category(CategoryRequestDto categoryRequestDto) {
        this.name = categoryRequestDto.getName();
    }

}