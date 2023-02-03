/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.entity;

import com.duong.training.dto.ProductDTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class CartLineInfo implements Serializable {
    private ProductDTO productDTO;
    private int quantity;
    private double amount;

    public CartLineInfo() {
        this.quantity = 0;
    }

    public double getAmount() {
        if(productDTO.getPromotions() != null){
            productDTO.getPromotions().forEach(
                    p -> this.amount = (productDTO.getPrice() * quantity) - ((productDTO.getPrice() * quantity) * p.getPercent() / 100)
            );
        }
        else {
            getAmountFist();
        }
        return amount;
    }
    public double getAmountFist() {
        this.amount = productDTO.getPrice() * quantity;
        return this.amount;
    }
}

