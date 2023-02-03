/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.service;

import java.util.ArrayList;
import java.util.List;

import com.duong.training.dto.RoleDTO;
import com.duong.training.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duong.training.entity.Role;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<RoleDTO> getAllRoleByUserName(String username) {
		List<Role> roles = roleRepository.findByUsers_UserName(username);
		List<RoleDTO> roleDTOs = new ArrayList<>();
		roles.forEach(p -> {
			RoleDTO roleDTO = modelMapper.map(p, RoleDTO.class);
			roleDTOs.add(roleDTO);
		});
		return roleDTOs;
	}

}
