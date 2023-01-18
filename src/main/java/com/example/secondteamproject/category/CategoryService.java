package com.example.secondteamproject.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategory() {
        List<Category> categoriesAllList = categoryRepository.findAllByOrderByNameAsc();
        List<Category> parentCateogryList = categoryRepository.findCategoriesByLayer(0);
        return parentCateogryList.stream().map(parentCateogryList1 -> new CategoryResponseDto(parentCateogryList1, categoriesAllList)).collect(Collectors.toList());
    }

    @Transactional
    public void createParentCategory(CategoryRequestDto req) {
        Optional<Category> checkCategoryIsPresent = categoryRepository.findByName(req.getName());
        if (checkCategoryIsPresent.isPresent()) {
            throw new IllegalArgumentException("Exist category name");
        }
        Category category = new Category(req);
        categoryRepository.save(category);
    }

    @Transactional
    public void createChildrenCategory(long id, CategoryRequestDto req) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Optional<Category> checkCategoryIsPresent = categoryRepository.findByName(req.getName());
        if (checkCategoryIsPresent.isPresent()) {
            throw new IllegalArgumentException("Exist category name");
        }
        int layer = category.getLayer() + 1;
        Category category1 = new Category(req.getName(), category.getName(), layer);
        categoryRepository.save(category1);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (notExistsCategory(id)) throw new IllegalStateException("Not exist category");
        categoryRepository.deleteById(id);
    }

    private boolean notExistsCategory(Long id) {
        return !categoryRepository.existsById(id);
    }
}