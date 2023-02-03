/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import com.duong.training.dto.ProductDTO;
import com.duong.training.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "8", required = false) Integer size,
            @RequestParam(defaultValue = "productId", required = false) String sortBy) {
        Page<ProductDTO> products = productService.getAllProduct(searchValue, page, size, sortBy);
        return ResponseEntity.ok(products);
    }


    @GetMapping("products/export")
    public ResponseEntity<List<ProductDTO>> exportProducts() {
        List<ProductDTO> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping("products/name/{name}")
    public ResponseEntity<List<ProductDTO>> findByNameProduct(@PathVariable("name") String name) {
        List<ProductDTO> productDTO = productService.findByNameProduct(name);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Integer idProduct) {
        ProductDTO productDTO = productService.findById(idProduct);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping("products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO result = productService.addProduct(productDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("products/import")
    public ResponseEntity<Object> importProduct(@RequestBody List<ProductDTO> listProductDTO) {
        Boolean check = productService.importProduct(listProductDTO);
        return ResponseEntity.ok(check);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable(name = "id") int id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Delete successfully");
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable("id") Integer idProduct) {
        productService.updateProduct(productDTO, idProduct);
        return ResponseEntity.ok("Update successfully");
    }

    @GetMapping("products/promotions/{page}/{size}")
    public ResponseEntity<Object> listProduct(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size) {

        Page<ProductDTO> productPage = productService.getAllProduct(PageRequest.of(page, size));
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("products/{page}/{size}")
    public ResponseEntity<Object> listProductHasPromotion(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size) {

        Page<ProductDTO> productPage = productService.getAllProductAndPromotion(PageRequest.of(page, size));

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("products/{categoryId}/{page}/{size}")
    public ResponseEntity<Page<ProductDTO>> getAllCategoryById(
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage = productService.getAllCategoryById(categoryId, pageable);
        return ResponseEntity.ok(productPage);
    }

    @GetMapping("/shop/{categoryId}")
    public ResponseEntity<Object> getProductByCategy(@PathVariable("categoryId") Integer idCategory) {
        List<ProductDTO> productDTO = productService.getProductByCategory(idCategory);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    //    filter product by category name
    @GetMapping("/category/name/{name}")
    public ResponseEntity<Object> getProductByCategoryName(@PathVariable("name") String categoryName) {
        List<ProductDTO> productDTO = productService.getProductByCategoryName(categoryName);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping("products/count")
    public ResponseEntity<Long> getNumberOfOrderOnHold() {
        Long productNum = productService.countProducts();
        return new ResponseEntity<>(productNum, HttpStatus.OK);
    }

    @GetMapping("products/newest-all")
    public ResponseEntity<List<ProductDTO>> getTopNewProduct() {
        List<ProductDTO> products = productService.getTopNewProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("products/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> users = productService.getAllProduct();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("products/save_all")
    public ResponseEntity<Boolean> saveAllUsers(@RequestBody List<ProductDTO> users) {
        Boolean result = productService.saveAllProducts(users);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("products/hot")
    public ResponseEntity<List<ProductDTO>> getHotProducts() {
        List<ProductDTO> productDTOS = productService.getHotProducts();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    /**
     * Get newest promoted products
     *
     * @return
     */
    @GetMapping("products/newest-promoted")
    public ResponseEntity<List<ProductDTO>> getTop8NewestPromotedProducts() {
        List<ProductDTO> productDTOList = productService.getTop8NewestPromotedProducts();
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }
}

