/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.security;

import java.util.ArrayList;
import java.util.List;

import com.duong.training.dto.RoleDTO;
import com.duong.training.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.duong.training.service.RoleService;
import com.duong.training.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username){
		try {
			UserDTO user = userService.findByUserName(username);
			List<RoleDTO> roles = roleService.getAllRoleByUserName(username);
			List<GrantedAuthority> grantList = new ArrayList<>();
			if (!roles.isEmpty()) {
				roles.forEach(o -> {
					GrantedAuthority authority = new SimpleGrantedAuthority(o.getName());
					grantList.add(authority);	
				});
			}
			return new User(user.getUserName(), user.getPassword(), user.isEnabled(), true, true,
					user.getStatus() == 1, grantList);
		} catch (Exception e) {
			throw new UsernameNotFoundException(username + " not found!");
		}
	}

}
