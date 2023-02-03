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

import com.duong.training.entity.Orders;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

	@Query("SELECT o FROM Orders o INNER JOIN o.users u"
			+ " WHERE u.userName LIKE %:searchValue%")
	Page<Orders> findByUsers_UserName(@Param("searchValue") String searchValue, Pageable pageable);

	@Query("SELECT o FROM Orders o INNER JOIN o.users u WHERE u.userName = :userName AND o.status = :status")
	List<Orders> findByUsers_UserName(@Param("userName") String userName, @Param("status") Integer status);

	Long countByStatus(Integer status);
	
}
