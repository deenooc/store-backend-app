package com.example.store.service;

import com.example.store.dto.CustomerDetailDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerDetailDTO createCustomer(@Valid Customer customer) {
        return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
    }

    public Page<CustomerDetailDTO> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customerMapper::customerToCustomerDTO);
    }

    public List<CustomerDetailDTO> findCustomersWithName(String nameSubstring) {
        return customerMapper.customersToCustomerDTOs(
                customerRepository.findByNameCaseInsensitiveSubstring(nameSubstring));
    }
}
