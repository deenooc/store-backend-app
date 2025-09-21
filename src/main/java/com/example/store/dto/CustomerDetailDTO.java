package com.example.store.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDetailDTO {
    private Long id;
    private String name;
    private List<OrderSummaryDTO> orders;
}
