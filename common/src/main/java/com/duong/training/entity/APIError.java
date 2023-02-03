/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	private List<APIErrorDetail> errorDetails;

	public APIError(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public APIError(HttpStatus status) {
		super();
		this.status = status;
		this.timestamp = LocalDateTime.now();
	}

	public APIError(HttpStatus status, Throwable ex) {
		super();
		this.status = status;
		this.message = "Unexpected error!";
		this.debugMessage = ex.getLocalizedMessage();
		this.timestamp = LocalDateTime.now();
	}

	public APIError(HttpStatus status, String message, Throwable ex) {
		super();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
		this.timestamp = LocalDateTime.now();
	}
}
