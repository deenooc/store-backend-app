package com.example.store.controller;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.service.CustomerService;

import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(Pageable pageable) {
        Page<CustomerDTO> customerDetails = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customerDetails);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> findCustomersWithName(
            @RequestParam(name = "name") @NotBlank String nameSubstring) {
        List<CustomerDTO> customerDetails = customerService.findCustomersWithName(nameSubstring);
        return ResponseEntity.ok(customerDetails);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
        CustomerDTO customerCreated = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
    }
}
