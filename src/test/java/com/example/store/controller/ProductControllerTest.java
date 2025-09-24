package com.example.store.controller;

import com.example.store.dto.ProductDTO;
import com.example.store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDto = new ProductDTO();
        productDto.setDescription("Sample Product");

        when(productService.createProduct(productDto)).thenReturn(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Sample Product"));

        verify(productService, times(1)).createProduct(productDto);
    }

    @Test
    void testGetAllProductsPaged() throws Exception {
        ProductDTO productDto = new ProductDTO();
        productDto.setDescription("Sample Product");
        Page<ProductDTO> productDtos = new PageImpl<>(List.of(productDto));

        when(productService.getAllProducts(any(Pageable.class))).thenReturn(productDtos);

        mockMvc.perform(get("/products/paged"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..description").value("Sample Product"));

        verify(productService, times(1)).getAllProducts(any(Pageable.class));
    }

    @Test
    void testGetAllProducts() throws Exception {
        ProductDTO productDto = new ProductDTO();
        productDto.setDescription("Sample Product");

        when(productService.getAllProducts()).thenReturn(List.of(productDto));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..description").value("Sample Product"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {
        ProductDTO productDto = new ProductDTO();
        productDto.setDescription("Sample Product");
        productDto.setId(1L);
        productDto.setOrderIds(List.of(101L));

        when(productService.getProductById(1L)).thenReturn(productDto);

        mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Sample Product"))
                .andExpect(jsonPath("$.orderIds[0]").value("101"));
    }
}
