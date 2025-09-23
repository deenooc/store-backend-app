package com.example.store.dto;

import lombok.Data;

import java.util.Set;

@Data
public class OrderDTO {
    private Long id;
    private String description;
    private CustomerSummaryDTO customer;
    private Set<ProductDTO> products;
}
