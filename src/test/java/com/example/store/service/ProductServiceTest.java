package com.example.store.service;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setDescription("Test Product");

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setDescription("Test Product");
    }

    @Test
    void test_createProduct_successfully() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDTO);

        // When
        ProductDTO result = productService.createProduct(productDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDescription()).isEqualTo("Test Product");

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void test_getAllProducts_with_pageable() {
        // Given
        Pageable pageable = mock(Pageable.class);
        Page<Product> products = new PageImpl<>(List.of(product));
        when(productRepository.findAll(pageable)).thenReturn(products);
        when(productMapper.toDto(product)).thenReturn(productDTO);

        // When
        Page<ProductDTO> result = productService.getAllProducts(pageable);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getDescription()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findAll(pageable);
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void test_getProductById_found() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDTO);

        // When
        ProductDTO result = productService.getProductById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDescription()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void test_getProductById_not_found() {
        // Given
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        ProductDTO result = productService.getProductById(2L);

        // Then
        assertThat(result).isNull();
        verifyNoInteractions(productMapper);
    }
}
