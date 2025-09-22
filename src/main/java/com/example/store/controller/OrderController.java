package com.example.store.controller;

import com.example.store.dto.OrderDetailDTO;
import com.example.store.entity.Order;
import com.example.store.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrders() {
        List<OrderDetailDTO> orderDetails = orderService.retrieveAllOrders();
        return ResponseEntity.ok(orderDetails);
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrder(@RequestBody Order order) {
        OrderDetailDTO orderDetail = orderService.createOrder(order);
        return new ResponseEntity<>(orderDetail, HttpStatus.CREATED);
    }
}
