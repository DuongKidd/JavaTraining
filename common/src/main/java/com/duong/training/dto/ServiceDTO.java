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

/**
 * Created by DuongBV on 17-04
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceDTO {
    private Integer serviceId;
    private String code;
    private String name;

    //1 - chuyển mạch 0 - không phải chuyển mạch
    private Integer transStatus;

    //1 - active , 0 - inactive, default = 1
    private Integer status;

}
