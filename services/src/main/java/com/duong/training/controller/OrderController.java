/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import com.duong.training.dto.OrdersDTO;
import com.duong.training.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private String messageSucces = "get successful";

    @GetMapping
    public ResponseEntity<Page<OrdersDTO>> getAllOrder(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(defaultValue = "orderId", required = false) String sortBy) {

        Page<OrdersDTO> orders = orderService.getAllOrder(searchValue, page, size, sortBy);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/member")
    public ResponseEntity<List<OrdersDTO>> getAllOrderMember(
            @RequestParam("username") String member,
            @RequestParam("status") Integer status) {
        List<OrdersDTO> orders = orderService.getAllOrderMember(member, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<OrdersDTO> viewOrder(
            @RequestParam("orderId") Integer orderid) {
        OrdersDTO orders = orderService.findOrderById(orderid);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @GetMapping("/cancel")
    public ResponseEntity<Boolean> cancelOrder(
            @RequestParam("orderId") Integer orderid) {
        return new ResponseEntity<>(orderService.cancelOrderStatus(orderid), HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<List<OrdersDTO>> getAllOrder() {
        List<OrdersDTO> orders = orderService.getAllOrderExport();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrdersDTO ordersDTO) {
        orderService.createOrder(ordersDTO);
        return new ResponseEntity<>("Create Successfully", HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<Object> importOrder(@RequestBody List<OrdersDTO> ordersDTOs) {
        orderService.createListOrder(ordersDTOs);
        return new ResponseEntity<>("Import Successfully", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> changeStatus(@RequestBody OrdersDTO ordersDTO) {
        boolean result = orderService.updateOrderStatus(ordersDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getNumberOfOrderOnHold() {
        Long orderNum = orderService.countOrderOnHold();
        return new ResponseEntity<>(orderNum, HttpStatus.OK);
    }
}
