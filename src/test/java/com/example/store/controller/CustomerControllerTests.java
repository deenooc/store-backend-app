package com.example.store.controller;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    private CustomerDTO customerDto;

    @BeforeEach
    void setUp() {
        customerDto = new CustomerDTO();
        customerDto.setName("John Doe");
        customerDto.setId(1L);
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerService.createCustomer(customer)).thenReturn(customerDto);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    void testGetAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(List.of(customerDto));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name").value("John Doe"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetAllCustomersPaged() throws Exception {
        Page<CustomerDTO> customerDetails = new PageImpl<>(List.of(customerDto));
        when(customerService.getAllCustomers(any(Pageable.class))).thenReturn(customerDetails);

        mockMvc.perform(get("/customers/paged"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name").value("John Doe"));

        verify(customerService, times(1)).getAllCustomers(any(Pageable.class));
    }

    @Test
    void testGetCustomersByNameSubstring() throws Exception {
        when(customerService.findCustomersWithName("do")).thenReturn(List.of(customerDto));

        mockMvc.perform(get("/customers/search?name=do"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name").value("John Doe"));

        verify(customerService, times(1)).findCustomersWithName("do");
    }

    @Test
    void testGetCustomersByNameSubstringValidationError() throws Exception {
        mockMvc.perform(get("/customers/search?name=")).andExpect(status().isBadRequest());

        verifyNoInteractions(customerService);
    }
}
