package com.example.store.service;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDTO createOrder(Order order) {
        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }

    public Page<OrderDTO> retrieveAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::orderToOrderDTO);
    }

    public OrderDTO findOrderById(Long id) {
        Optional<Order> orderResult = orderRepository.findById(id);
        return orderResult.map(orderMapper::orderToOrderDTO).orElse(null);
    }

    public List<OrderDTO> retrieveAllOrders() {
        return orderMapper.ordersToOrderDTOs(orderRepository.findAll());
    }
}
