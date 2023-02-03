/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.duong.training.entity.Users;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query("from Users u where u.userId = ?1")
    Users findByUserId(Integer idUsers);
    
    Users findByEmail(String email);
    
    Users findByUserName(String username);
    
    @Query("SELECT e FROM Users e INNER JOIN e.roles r"
    		+ " WHERE (e.userName LIKE %:searchValue% OR e.fullName LIKE %:searchValue% OR e.email LIKE %:searchValue%)"
    		+ " AND r.name LIKE :roleName")
	Page<Users> findBySearchValueAndRoles_Name(@Param("searchValue") String searchValue, @Param("roleName") String roleName, Pageable pageable);

	Long countByStatus(Integer status);

    List<Users> findByRoles_Name(String role_user);
}
