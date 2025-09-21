package com.example.store.controller;

import com.example.store.dto.CustomerDetailDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDetailDTO>> getAllCustomers() {
        List<CustomerDetailDTO> customerDetails = customerMapper.customersToCustomerDTOs(customerRepository.findAll());
        return ResponseEntity.ok(customerDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerDetailDTO> createCustomer(@RequestBody Customer customer) {
        CustomerDetailDTO customerDetail = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
        return new ResponseEntity<>(customerDetail, HttpStatus.CREATED);
    }
}
