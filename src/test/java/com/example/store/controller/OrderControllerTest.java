package com.example.store.controller;

import com.example.store.dto.CustomerSummaryDTO;
import com.example.store.dto.OrderDetailDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setId(1L);

        order = new Order();
        order.setDescription("Test Order");
        order.setId(1L);
        order.setCustomer(customer);
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderDetailDTO orderCreated = getOrderDetailDto(order);
        when(orderService.createOrder(order)).thenReturn(orderCreated);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Order"))
                .andExpect(jsonPath("$.customer.name").value("John Doe"));
    }

    @Test
    void testGetOrders() throws Exception {
        OrderDetailDTO orderDetail = getOrderDetailDto(order);

        when(orderService.retrieveAllOrders()).thenReturn(List.of(orderDetail));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..description").value("Test Order"))
                .andExpect(jsonPath("$..customer.name").value("John Doe"));
    }

    @Test
    void testGetOrderById() throws Exception {
        OrderDetailDTO orderDetail = getOrderDetailDto(order);
        Long id = 1L;

        when(orderService.findOrderById(id)).thenReturn(orderDetail);

        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Order"))
                .andExpect(jsonPath("$.customer.name").value("John Doe"));
    }

    @Test
    void testGetOrderByIdNotFound() throws Exception {
        Long id = 1L;

        when(orderService.findOrderById(id)).thenReturn(null);

        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order with id = 1 not found."));
    }

    private OrderDetailDTO getOrderDetailDto(Order order) {
        OrderDetailDTO orderDetail = new OrderDetailDTO();
        orderDetail.setId(order.getId());
        orderDetail.setDescription(order.getDescription());

        CustomerSummaryDTO customerSummary = new CustomerSummaryDTO();
        customerSummary.setId(order.getCustomer().getId());
        customerSummary.setName(order.getCustomer().getName());
        orderDetail.setCustomer(customerSummary);

        return orderDetail;
    }
}
