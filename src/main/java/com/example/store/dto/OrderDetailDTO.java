package com.example.store.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Long id;
    private String description;
    private CustomerSummaryDTO customer;
}
