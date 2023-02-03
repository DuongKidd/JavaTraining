/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO extends AbstractDTO {
    private int productId;
    private String productName;
    private String image;
    private String description;
    private double price;
    private int[] categoryIds;

    List<CategoryDTO> categories;
    List<PromotionDTO> promotions;

    public ProductDTO(ProductDTO productDTO) {
        this.productId = productDTO.getProductId();
        this.productName = productDTO.getProductName();
        this.image = productDTO.getImage();
        this.description = productDTO.getDescription();
        this.price = productDTO.getPrice();
        this.categoryIds = productDTO.getCategoryIds();
        this.categories = productDTO.getCategories();
        this.promotions = productDTO.getPromotions();
    }

}

