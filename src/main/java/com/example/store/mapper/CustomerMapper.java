package com.example.store.mapper;

import com.example.store.dto.CustomerDetailDTO;
import com.example.store.entity.Customer;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDetailDTO customerToCustomerDTO(Customer customer);

    List<CustomerDetailDTO> customersToCustomerDTOs(List<Customer> customer);
}
