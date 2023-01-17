package com.example.secondteamproject.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createParentCategory(@RequestBody CategoryRequestDto req) {
            categoryService.createParentCategory(req);
            return new ResponseEntity<>("Create parent category ", HttpStatus.CREATED);
    }

    @PostMapping("/{categoryParentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createChildrenCategory(@PathVariable("categoryParentId") long id, @RequestBody CategoryRequestDto req) {
        categoryService.createChildrenCategory(id, req);
        return new ResponseEntity<>("Create children category ", HttpStatus.CREATED);

    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Delete children category ", HttpStatus.OK);

    }
}