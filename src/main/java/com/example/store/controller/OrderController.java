package com.example.store.controller;

import com.example.store.dto.OrderDetailDTO;
import com.example.store.entity.Order;
import com.example.store.exception.ResourceNotFoundException;
import com.example.store.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrders() {
        List<OrderDetailDTO> orderDetails = orderService.retrieveAllOrders();
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderById(@PathVariable Long id) {
        OrderDetailDTO orderDetail = orderService.findOrderById(id);
        if (orderDetail == null) {
            String message = String.format("Order with id = %d not found.", id);
            LOG.info(message);
            throw new ResourceNotFoundException(message);
        }
        return ResponseEntity.ok(orderDetail);
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrder(@RequestBody Order order) {
        OrderDetailDTO orderDetail = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetail);
    }
}
