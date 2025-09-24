package com.example.store.service;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomerSuccessfully() {
        Customer customer = mock(Customer.class);
        Customer customerCreated = mock(Customer.class);
        CustomerDTO customerDto = mock(CustomerDTO.class);

        // Given
        when(customerRepository.save(customer)).thenReturn(customerCreated);
        when(customerMapper.customerToCustomerDTO(customerCreated)).thenReturn(customerDto);

        // When
        CustomerDTO actual = customerService.createCustomer(customer);

        // Then
        assertThat(actual).isEqualTo(customerDto);
    }

    @Test
    void testGetAllCustomers() {
        Customer customer = mock(Customer.class);
        Pageable pageable = mock(Pageable.class);
        Page<Customer> customers = new PageImpl<>(List.of(customer));
        CustomerDTO customerDto = mock(CustomerDTO.class);

        // Given
        when(customerRepository.findAll(pageable)).thenReturn(customers);
        when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDto);

        // When
        Page<CustomerDTO> actual = customerService.getAllCustomers(pageable);

        // Then
        assertThat(actual.getTotalElements()).isEqualTo(1);

        CustomerDTO actualCustomer = actual.getContent().get(0);
        assertThat(actualCustomer).isEqualTo(customerDto);
    }
}
