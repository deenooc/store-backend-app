package com.example.store.mapper;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Order;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "orderIds", source = "orders", qualifiedByName = "ordersToIds")
    ProductDTO toDto(Product order);

    @Named("ordersToIds")
    default List<Long> ordersToIds(List<Order> orders) {
        return orders == null ? List.of() : orders.stream().map(Order::getId).collect(Collectors.toList());
    }

    // helper
    default java.util.List<Long> mapOrdersToIds(java.util.List<com.example.store.entity.Order> orders) {
        return orders == null
                ? java.util.List.of()
                : orders.stream().map(com.example.store.entity.Order::getId).toList();
    }

    List<ProductDTO> toDtos(List<Product> products);
}
