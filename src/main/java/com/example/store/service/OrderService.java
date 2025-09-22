package com.example.store.service;

import com.example.store.dto.OrderDetailDTO;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDetailDTO createOrder(Order order) {
        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }

    public List<OrderDetailDTO> retrieveAllOrders() {
        return orderMapper.ordersToOrderDTOs(orderRepository.findAll());
    }
}
