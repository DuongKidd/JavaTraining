/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by DucTD on 17/4/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_code")
public class AppCode extends BaseAudit {
    @Id
    @Column(name = "app_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appCodeId;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    //1 - active , 0 - inactive, default = 1
    @Column(name = "status")
    private Integer status;
}
