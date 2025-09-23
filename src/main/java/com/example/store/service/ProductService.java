package com.example.store.service;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setDescription(dto.getDescription());
        Product saved = productRepository.save(product);
        return toDTO(saved);
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(this::toDTO).orElse(null);
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setOrderIds(
                product.getOrders() == null
                        ? Set.of()
                        : product.getOrders().stream().map(Order::getId).collect(Collectors.toSet()));
        return dto;
    }
}
