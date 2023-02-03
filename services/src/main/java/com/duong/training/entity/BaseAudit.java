/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by DucTD on 17/4/2020
 */
@Data
@MappedSuperclass
public class BaseAudit implements Serializable {
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