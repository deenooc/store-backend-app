package com.example.store.mapper;

import com.example.store.dto.CustomerSummaryDTO;
import com.example.store.dto.OrderDetailDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDetailDTO orderToOrderDTO(Order order);

    List<OrderDetailDTO> ordersToOrderDTOs(List<Order> orders);

    CustomerSummaryDTO orderToOrderCustomerDTO(Customer customer);
}
