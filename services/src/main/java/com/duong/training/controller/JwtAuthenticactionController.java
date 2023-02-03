/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import com.duong.training.entity.LoginRequest;
import com.duong.training.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class JwtAuthenticactionController {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(JwtAuthenticactionController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtils jwtTokenUtil;

	@PostMapping("generate_token")
	public ResponseEntity<Object> createAuthenticationToken(
			@RequestBody LoginRequest loginRequest) {
		Authentication auth;
		try {
			auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (Exception e) {
			LOGGER.error("Responding with Bad credentials error. Message - {}", e.getMessage());
			throw new BadCredentialsException("Responding with Bad credentials error");
		}
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());
		return ResponseEntity.ok(jwt);
	}

}
