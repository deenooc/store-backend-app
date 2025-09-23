package com.example.store.mapper;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product order);

    Product toEntity(ProductDTO dto);
}
