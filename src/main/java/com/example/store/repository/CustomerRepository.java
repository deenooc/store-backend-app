package com.example.store.repository;

import com.example.store.entity.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(
            "SELECT c FROM Customer c LEFT JOIN FETCH c.orders WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :nameSubstring, '%'))")
    List<Customer> findByNameCaseInsensitiveSubstring(@Param("nameSubstring") String nameSubstring);

    @EntityGraph(attributePaths = {"orders"})
    Page<Customer> findAll(Pageable pageable);
}
