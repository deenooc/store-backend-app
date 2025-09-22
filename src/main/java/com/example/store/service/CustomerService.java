package com.example.store.service;

import com.example.store.dto.CustomerDetailDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDetailDTO createCustomer(Customer customer) {
        return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
    }

    public List<CustomerDetailDTO> getAllCustomers() {
        return customerMapper.customersToCustomerDTOs(customerRepository.findAll());
    }

    public List<CustomerDetailDTO> findCustomersWithName(String nameSubstring) {
        return customerMapper.customersToCustomerDTOs(
                customerRepository.findByNameCaseInsensitiveSubstring(nameSubstring));
    }
}
