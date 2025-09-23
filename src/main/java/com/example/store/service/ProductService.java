package com.example.store.service;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setDescription(dto.getDescription());
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toDto).orElse(null);
    }

    public List<ProductDTO> getAllProducts() {
        return productMapper.toDtos(productRepository.findAll());
    }
}
