package com.example.store.service;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder() {
        Order order = mock(Order.class);
        Order orderSaved = mock(Order.class);
        OrderDTO orderDto = mock(OrderDTO.class);

        // Given
        when(orderRepository.save(order)).thenReturn(orderSaved);
        when(orderMapper.orderToOrderDTO(orderSaved)).thenReturn(orderDto);

        // When
        OrderDTO actual = orderService.createOrder(order);

        // Then
        assertThat(actual).isEqualTo(orderDto);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).orderToOrderDTO(orderSaved);
    }

    @Test
    void testFindAllOrders() {
        Order order = mock(Order.class);
        Page<Order> orders = new PageImpl<>(List.of(order));
        OrderDTO orderDetail = mock(OrderDTO.class);
        List<OrderDTO> orderDetails = List.of(orderDetail);

        Pageable pageable = mock(Pageable.class);

        // Given
        when(orderRepository.findAll(pageable)).thenReturn(orders);
        when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDetail);

        // When
        Page<OrderDTO> actual = orderService.retrieveAllOrders(pageable);

        // Then
        assertThat(actual.getContent()).isEqualTo(orderDetails);
        verify(orderRepository, times(1)).findAll(pageable);
        verify(orderMapper, times(1)).orderToOrderDTO(order);
    }

    @Test
    void testFindOrderById() {
        Order order = mock(Order.class);
        OrderDTO orderDto = mock(OrderDTO.class);

        Long id = 1L;

        // Given
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderDTO(order)).thenReturn(orderDto);

        // When
        OrderDTO actual = orderService.findOrderById(id);

        // Then
        assertThat(actual).isEqualTo(orderDto);
        verify(orderRepository, times(1)).findById(id);
        verify(orderMapper, times(1)).orderToOrderDTO(order);
    }
}
