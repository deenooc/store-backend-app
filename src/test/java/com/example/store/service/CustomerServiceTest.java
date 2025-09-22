package com.example.store.service;

import com.example.store.dto.CustomerDetailDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackageClasses = CustomerMapper.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, customerMapper);
    }

    @Test
    void test_create_customer_successfully() {
        Customer customer = mock(Customer.class);
        Customer customerCreated = getCustomer();

        // Given
        when(customerRepository.save(customer)).thenReturn(customerCreated);

        // When
        CustomerDetailDTO actual = customerService.createCustomer(customer);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getName()).isEqualTo("Dan Bee");
        assertThat(actual.getOrders()).isNotEmpty();
        assertThat(actual.getOrders().get(0).getId()).isEqualTo(100L);
        assertThat(actual.getOrders().get(0).getDescription()).isEqualTo("Bags of flour");
    }

    @Test
    void test_get_all_customers() {
        Customer customer = getCustomer();
        Pageable pageable = mock(Pageable.class);
        Page<Customer> customers = new PageImpl<>(List.of(customer));

        // Given
        when(customerRepository.findAll(pageable)).thenReturn(customers);

        // When
        Page<CustomerDetailDTO> actual = customerService.getAllCustomers(pageable);

        // Then
        assertThat(actual).isNotEmpty();
        assertThat(actual.getTotalElements()).isEqualTo(1);

        CustomerDetailDTO actualCustomer = actual.getContent().get(0);
        assertThat(actualCustomer.getName()).isEqualTo("Dan Bee");
        assertThat(actualCustomer.getOrders()).isNotEmpty();
        assertThat(actualCustomer.getOrders().get(0).getId()).isEqualTo(100L);
        assertThat(actualCustomer.getOrders().get(0).getDescription()).isEqualTo("Bags of flour");
    }

    private static Customer getCustomer() {
        Customer customerCreated = new Customer();
        customerCreated.setId(1L);
        customerCreated.setName("Dan Bee");

        Order order = new Order();
        order.setCustomer(customerCreated);
        order.setId(100L);
        order.setDescription("Bags of flour");
        customerCreated.setOrders(List.of(order));
        return customerCreated;
    }
}
