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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by DucTD on 17/4/2020
 */
@Entity
@Table(name = "bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank extends BaseAudit {
    @Id
    @Column(name = "bank_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankId;

    @Column(name = "code")
    private String code;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "prefix_card")
    private String prefixCard;

    //1 - active , 0 - inactive,default = 1
    @Column(name = "status")
    private Integer status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_datetime")
    @CreationTimestamp
    private LocalDateTime createdDatetime;


    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_datetime")
    @UpdateTimestamp
    private LocalDateTime modifiedDatetime;

}
