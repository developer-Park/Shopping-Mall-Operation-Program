package com.example.secondteamproject.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Api(tags = "category")
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카테고리 조회", notes = "모든 카테고리를 조회")
    public List<CategoryResponseDto> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "부모 카테고리 생성", notes = "부모 카테고리를 생성")
    public ResponseEntity<String> createParentCategory(@RequestBody CategoryRequestDto req) {
            categoryService.createParentCategory(req);
            return new ResponseEntity<>("Create parent category ", HttpStatus.CREATED);
    }

    @PostMapping("/{categoryParentId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "자식 카테고리 생성", notes = "자식 카테고리를 생성")
    public ResponseEntity<String> createChildrenCategory(@PathVariable("categoryParentId") long id, @RequestBody CategoryRequestDto req) {
        categoryService.createChildrenCategory(id, req);
        return new ResponseEntity<>("Create children category ", HttpStatus.CREATED);

    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카테고리 삭제", notes = "카테고리를 삭제")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Delete children category ", HttpStatus.OK);

    }
}