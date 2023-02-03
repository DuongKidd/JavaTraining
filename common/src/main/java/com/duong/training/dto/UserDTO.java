/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.duong.training.dto.validate.annotation.ValidEmail;
import com.duong.training.dto.validate.annotation.ValidMatchingPassword;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ValidMatchingPassword
public class UserDTO extends AbstractDTO {
	
	private int userId;
	
	@NotNull
	@NotEmpty
	private String fullName;
	
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String userName;
	
	@NotNull
	@NotEmpty
	private String password;
	private String matchingPassword;
	
	private boolean enabled;
		
}
