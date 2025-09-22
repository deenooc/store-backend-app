package com.example.store.service;

import com.example.store.dto.OrderDetailDTO;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDetailDTO createOrder(Order order) {
        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }

    public Page<OrderDetailDTO> retrieveAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::orderToOrderDTO);
    }

    public OrderDetailDTO findOrderById(Long id) {
        Optional<Order> orderResult = orderRepository.findById(id);
        return orderResult.map(orderMapper::orderToOrderDTO).orElse(null);
    }
}
