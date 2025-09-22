package com.example.store.service;

import com.example.store.dto.OrderDetailDTO;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
    void test_create_order_successfully() {
        Order order = mock(Order.class);
        Order orderSaved = mock(Order.class);
        OrderDetailDTO orderDetailDTO = mock(OrderDetailDTO.class);

        // Given
        when(orderRepository.save(order)).thenReturn(orderSaved);
        when(orderMapper.orderToOrderDTO(orderSaved)).thenReturn(orderDetailDTO);

        // When
        OrderDetailDTO actual = orderService.createOrder(order);

        // Then
        assertThat(actual).isEqualTo(orderDetailDTO);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).orderToOrderDTO(orderSaved);
    }

    @Test
    void test_find_all_orders() {
        List<Order> orders = List.of(mock(Order.class));
        List<OrderDetailDTO> orderDetails = List.of(mock(OrderDetailDTO.class));

        // Given
        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.ordersToOrderDTOs(orders)).thenReturn(orderDetails);

        // When
        List<OrderDetailDTO> actual = orderService.retrieveAllOrders();

        // Then
        assertThat(actual).isEqualTo(orderDetails);
        verify(orderRepository, times(1)).findAll();
        verify(orderMapper, times(1)).ordersToOrderDTOs(orders);
    }
}
