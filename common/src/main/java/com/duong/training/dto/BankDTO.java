/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * Created by DuongBV on 17-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO extends BaseAuditDTO {

    private Integer bankId;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Mã phải gồm chữ cái viết hoa và số")
    private String code;

    private String shortName;

    @NotBlank(message = "Không được bỏ trống")
    private String legalName;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^[0-9]+$", message = "Đầu số thẻ chỉ được nhập số")
    private String prefixCard;

    //1 - active , 0 - inactive,default = 1
    private Integer status;
    public BankDTO(String code, String legalName, String prefixCard, int status,
                   String createdBy, LocalDateTime createdDatetime,
                   String modifiedBy, LocalDateTime modifiedDatetime) {
        super(createdBy, createdDatetime, modifiedBy, modifiedDatetime);
        this.code = code;
        this.legalName = legalName;
        this.prefixCard = prefixCard;
        this.status = status;
    }

    @Override
    public String toString() {
        return "BankDTO{" +
                "bankId=" + bankId +
                ", code='" + code + '\'' +
                ", shortName='" + shortName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", prefixCard='" + prefixCard + '\'' +
                ", status=" + status +
                '}';
    }
}
