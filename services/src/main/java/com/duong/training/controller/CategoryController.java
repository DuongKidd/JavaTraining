/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import java.util.List;

import com.duong.training.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duong.training.service.CategoryService;

@RestController
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("categories/name/{name}")
    public ResponseEntity<Object> findByNameCategory(@PathVariable("name") String name) {
        return ResponseEntity.ok(categoryService.findByNameCategory(name));
    }

    @GetMapping("categories/{categoryId}")
    public ResponseEntity<Object> findByIdCategory(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

    @GetMapping("categories")
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(defaultValue = "categoryId", required = false) String sortBy) {
        Page<CategoryDTO> categories = categoryService.getAllCategory(searchValue, page, size, sortBy);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("categories")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.addCategory(categoryDTO));
    }

    @PostMapping("categories/import")
    public ResponseEntity<Object> importFile(@RequestBody List<CategoryDTO> categoryDTOList) {
        boolean importResult = categoryService.addCategoryImport(categoryDTOList);
        return new ResponseEntity<>(importResult, HttpStatus.OK);
    }

    @DeleteMapping("categories")
    public ResponseEntity<Object> deleteCategory(@RequestParam(name = "id") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Delete successfully");
    }

    @PutMapping("categories/{categoryId}")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable("categoryId") int categoryId) {
        categoryService.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.ok("Update successfully");
    }

    @GetMapping("categories/categories")
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("categories/count")
    public ResponseEntity<Long> getNumberOfUserIsUnblocked() {
        Long categoryNum = categoryService.countCategory();
        return new ResponseEntity<>(categoryNum, HttpStatus.OK);
    }

    @GetMapping("categories/productName/{productName}")
    public ResponseEntity<List<CategoryDTO>> findRoleByUsername(
            @PathVariable("productName") String productName) {
        List<CategoryDTO> roleDTOs = categoryService.getAllCateByProductName(productName);
        return ResponseEntity.ok(roleDTOs);
    }
}
