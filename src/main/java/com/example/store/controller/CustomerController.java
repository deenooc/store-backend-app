package com.example.store.controller;

import com.example.store.dto.CustomerDetailDTO;
import com.example.store.entity.Customer;
import com.example.store.service.CustomerService;

import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<List<CustomerDetailDTO>> getAllCustomers() {
        List<CustomerDetailDTO> customerDetails = customerService.getAllCustomers();
        return ResponseEntity.ok(customerDetails);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDetailDTO>> findCustomersWithName(
            @RequestParam(name = "name") @NotBlank String nameSubstring) {
        List<CustomerDetailDTO> customerDetails = customerService.findCustomersWithName(nameSubstring);
        return ResponseEntity.ok(customerDetails);
    }

    @PostMapping
    public ResponseEntity<CustomerDetailDTO> createCustomer(@RequestBody Customer customer) {
        CustomerDetailDTO customerCreated = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
    }
}
