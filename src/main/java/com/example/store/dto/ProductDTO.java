package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.util.Set;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank
    private String description;

    private Set<Long> orderIds;
}
