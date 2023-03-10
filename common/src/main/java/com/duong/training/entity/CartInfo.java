/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.entity;

import com.duong.training.dto.ProductDTO;
import com.duong.training.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartInfo implements Serializable {

    private int cartId;
    private UserDTO userDTO;
    private transient List<CartLineInfo> cartLines = new ArrayList<>();

    public CartLineInfo findLineByCode(Integer code) {
        for (CartLineInfo line : this.cartLines) {
            if (line.getProductDTO().getProductId() == code ) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(ProductDTO productDTO, int quantity) {
        CartLineInfo line = this.findLineByCode(productDTO.getProductId());

        if (line == null) {
            line = new CartLineInfo();
            line.setQuantity(0);
            line.setProductDTO(productDTO);
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }

    public void removeProduct(ProductDTO productDTO) {
        CartLineInfo line = this.findLineByCode(productDTO.getProductId());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }

    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLineInfo line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    public double getAmountTotal() {
        double total = 0;
        for (CartLineInfo line : this.cartLines) {
            total += line.getAmount();
        }
        return total;
    }

    public void addProduct2(ProductDTO productDTO, int quantity) {
        CartLineInfo line = this.findLineByCode(productDTO.getProductId());
        if(line != null) {
            if (quantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }
}

