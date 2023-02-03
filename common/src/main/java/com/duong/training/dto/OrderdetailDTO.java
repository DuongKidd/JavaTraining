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
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderdetailDTO extends AbstractDTO {

	private int deltailId;
    private ProductDTO productDTO;
    private int quantity;
    private double price;
    private double amount;

    public OrderdetailDTO() {
        this.quantity = 0;
    }

    public double getAmount() {
        return this.productDTO.getPrice() * this.quantity;
    }
    
}
