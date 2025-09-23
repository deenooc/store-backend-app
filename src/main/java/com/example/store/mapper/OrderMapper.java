package com.example.store.mapper;

import com.example.store.dto.CustomerSummaryDTO;
import com.example.store.dto.OrderDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = ProductMapper.class)
public interface OrderMapper {
    OrderDTO orderToOrderDTO(Order order);

    Order toEntity(OrderDTO dto);

    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);

    CustomerSummaryDTO orderToOrderCustomerDTO(Customer customer);
}
