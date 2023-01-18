package com.example.secondteamproject.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
    private int layer;
    private List<CategoryResponseDto> children;

    public CategoryResponseDto(Category parentCategory, List<Category> listAllcategory) {
        this.id = parentCategory.getId();
        this.name = parentCategory.getName();
        this.layer = parentCategory.getLayer();

        List<CategoryResponseDto> categoryResponsDtos = new ArrayList<>();
        for (Category ca : listAllcategory){
            if (parentCategory.getLayer() + 1 == ca.getLayer() && ca.getParent().equals(parentCategory.getName())) {
                categoryResponsDtos.add(new CategoryResponseDto(ca,listAllcategory));
            }
            this.children = categoryResponsDtos;
        }
    }
}