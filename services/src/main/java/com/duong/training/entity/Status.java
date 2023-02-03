/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.entity;

/**
 * fres-parent
 *
 * @author thanhdat
 * @created_at 21/04/2020 - 2:34 PM
 * @since 21/04/2020
 */
public enum Status {

    ACTIVE(1), INACTIVE(0);

    private final int value;

    Status(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
